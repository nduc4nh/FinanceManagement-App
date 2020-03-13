package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Entities.OptionalEntity;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.LanguageService;
import com.bookstore.forreal.Model.Services.OptionalService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;



@RestController
public class LanguageController {
    @Autowired
    LanguageService service;
    @Autowired
    OptionalService subservice;
    
    @GetMapping("GET/Language")
    public List<Language> getAllLanguage()
    {
        return service.findAll();
    }
    @GetMapping("GET/Language/{id}")
    public Optional<Language> getLanguageById(@PathVariable("id") Integer id)
    {
        return service.findById(id);
    }

    @PostMapping("POST/Language")
    public void addLanguage(@RequestParam("name") String name ,@Nullable@RequestParam("price") String price)
    {   
        List<Language> tmpLanguages = service.findAll();
        for (Language ele:tmpLanguages) {
            if (ele.getName().equals(name)) return;
        }
        Language newLanguage = new Language(name);
        try{
            Long newprice = Long.parseLong(preprocessString.doString(price));
            if (newprice < 0) return;
            newLanguage.setPrice(newprice);
        }
        catch (Exception e)
        {
            newLanguage.setPrice(0);
        }
        newLanguage.setCreatedDate(new Date());
        service.Save(newLanguage);
    }

    @RequestMapping("PUT/Language/{id}")
    /*public void modifyLanguage(@PathVariable("id") int id,@RequestBody Language newform)
    {
        if (!service.existById(id))
        {
            return;
        }
        Language thislang = service.findById(id).get();
        thislang.setName(newform.getName());
        thislang.setBooks(newform.getBooks());
        thislang.setModifiledDate(new Date());
        service.Save(thislang);
    }*/
    public void modifyLanguage(@PathVariable("id") int id,
    @Nullable@RequestParam("price") String price,
    @Nullable@RequestParam("name") String name,
    @Nullable@RequestParam("newbook") String newbook,
    @Nullable@RequestParam("removebook") String removebook)
    {
        if (!service.existById(id))
        {
            return;
        }
        Language thislang = service.findById(id).get();
        List<Language> langs = service.findAll();

        try{
            Long newprice = Long.parseLong(preprocessString.doString(price));
            if (newprice > 0) thislang.setPrice(newprice);
        }
        catch (Exception e){}
        
        bindingAction langAndbook = new bindingAction<Language,Book,LanguageService,BookService>();
        if (newbook != null) langAndbook.addToSource(service, subservice, thislang, newbook, langs);
        if (removebook != null) langAndbook.removeFromSource(service, subservice, thislang, removebook, langs);
        if (name != null)
        {
            for (Language ele:langs)
            {
                String tmp = ele.getName();
                if (preprocessString.doString(tmp) == preprocessString.doString(name)
                )
                {
                    name = ele.getName();
                    break;
                }
            }
            thislang.setName(name);
        }
        thislang.setModifiledDate(new Date());
        service.Save(thislang);
    }

    @RequestMapping("DELETE/Language/{id}")
    public void deleteLanguage(@PathVariable("id") int id) throws Exception
    {
        if (service.existById(id))
        {
            throw new Exception("Invalid id");
        }
        Language tmplang = service.findById(id).get();
        List<OptionalEntity> books = tmplang.getOptionalbook();
        for(OptionalEntity ele:books)
        {
            ele.setLang(null);
        }
        tmplang.setOptionalbook(null);
        subservice.saveAll(books); 
        service.deleteById(id);
    }
}


