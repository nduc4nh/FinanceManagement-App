package com.bookstore.forreal.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Image;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Entities.OptionalEntity;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.ImageService;
import com.bookstore.forreal.Model.Services.LanguageService;
import com.bookstore.forreal.Model.Services.OptionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class OptionalController
{
    @Autowired
    OptionalService service;
    @Autowired
    BookService subserviceBo;
    @Autowired
    LanguageService subserviceLa;
    @Autowired
    ImageService subserviceIm;

    @GetMapping("GET/Book/{idbook}/optional")
    public List<OptionalEntity>getOptional(@PathVariable("idbook") int idbook)
    {
        Book tmpbook = subserviceBo.findById(idbook).get();
        return tmpbook.getOptionalbooks();
    }
    
    @GetMapping("GET/Book/{idbook}/optional/{id}")
    public OptionalEntity getOptional(@PathVariable("idbook") int idbook,@PathVariable("id") int id) throws Exception
    {
        if(!subserviceBo.existById(idbook)) throw new Exception("Not found!");
        if (!service.existById(id)) throw new Exception("Not found!");
        
        Book book = subserviceBo.findById(idbook).get();
        OptionalEntity optionbook = service.findById(id).get();
        if (book.getOptionalbooks().contains(optionbook)) return optionbook;
        throw new Exception("THis version doesn't exist");
    }
    
    @PutMapping("PUT/Book/{idbook}/optional/{id}")
    public void modifiedOptionalbook(@PathVariable("idbook") int idbook,@PathVariable("id") int id,
    @Nullable@RequestParam("lang") String lang,
    @Nullable@RequestParam("img") String img) throws Exception
    {
        if (!subserviceBo.existById(idbook))
        {
            throw new Exception("Invalid id");
        }
        if(!service.existById(id))
        {
            throw new Exception("Invalid id");
        }
        Book thisbook = subserviceBo.findById(idbook).get();
        OptionalEntity draft = service.findById(id).get();
        List <OptionalEntity> optionalList = thisbook.getOptionalbooks();
        if (!optionalList.contains(draft)) 
        {   
            throw new Exception("Invalid version");
        }
        if (lang != null) 
        {
            Language thislang = subserviceLa.findByName(lang);
            if (thisbook.getLanguages().contains(thislang))
            {   
                draft.setLang(thislang);
                draft.setModifiedDate(new Date());
            }
        }
        Image thisimg = subserviceIm.findByName(img);
        if (thisimg != null)
        {
            if (draft.getLang().getImages().contains(thisimg) &&
            thisbook.getImages().contains(thisimg)) draft.setImg(thisimg);
        }
        else
        {
            Image newimg = new Image(img,thisbook);
            draft.getLang().getImages().add(newimg);
            draft.setImg(newimg);
            subserviceIm.Save(newimg);
        }         
        service.Save(draft);                
    }
    
    @PostMapping("POST/Book/{idbook}/optional")
    public void addCustomizeBook(@PathVariable("idbook") int idbook,@Nullable@RequestParam("lang") String lang,
    @Nullable@RequestParam("img") String imgpath) throws Exception
    {
        if (!subserviceBo.existById(idbook))
        {
            return;
        }
        Book thisbook = subserviceBo.findById(idbook).get();
        Language thislang = subserviceLa.findByName(lang);
        List<OptionalEntity> optionalbooks = thisbook.getOptionalbooks();
        for (OptionalEntity ele:optionalbooks)
        {
            if (ele.getLang().equals(thislang)) throw new Exception("This version has already existed");
        }
        OptionalEntity newoptionalbook = new OptionalEntity(thisbook,thislang); 
        if (imgpath == null) newoptionalbook.setImg(null);
        else 
        {   
            Image thisimg = subserviceIm.findByName(imgpath);
                if (thisimg != null)
                {
                    if (thislang.getImages().contains(thisimg) &&
                        thisbook.getImages().contains(thisimg)) newoptionalbook.setImg(thisimg);
                }
                else
                {
                    Image newimg = new Image(imgpath,thisbook);
                    newoptionalbook.getLang().getImages().add(newimg);
                    newoptionalbook.setImg(newimg);
                    subserviceIm.Save(newimg);
                } 
        }
        newoptionalbook.setCreatedDate(new Date());
        optionalbooks.add(newoptionalbook);
        subserviceBo.Save(thisbook);
        service.Save(newoptionalbook);
    }
    
    @DeleteMapping("DELETE/optional/{id}")
    public void deleteOptional(@PathVariable int id) throws Exception
    {
        if (!service.existById(id))
        {
            throw new Exception("Invalid id");
        } 
        service.deleteById(id);
    }
}