package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.LanguageService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class LanguageController {
    @Autowired
    LanguageService service;
    @Autowired
    BookService subservice;
    
    @GetMapping("Language/get")
    public List<Language> getAllLanguage()
    {
        return service.findAll();
    }
    @GetMapping("Language/get/{id}")
    public Optional<Language> getLanguageById(@PathVariable("id") Integer id)
    {
        return service.findById(id);
    }

    @PostMapping("Language/post")
    public void addLanguage(@RequestParam("name") String name)
    {
        List<Language> tmpLanguages = service.findAll();
        for (Language ele:tmpLanguages) {
            if (ele.getName().equals(name)) return;
        }
        Language newLanguage = new Language(name);
        newLanguage.setCreatedDate(new Date());
        service.Save(newLanguage);
    }

    @RequestMapping("Language/put/{id}")
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
    public void modifyLanguage(@PathVariable("id") int id,@RequestParam("name") String name,@RequestParam("new") String newone,@RequestParam("remove") String removeone)
    {
        if (!service.existById(id))
        {
            return;
        }
        Language thislang = service.findById(id).get();
        List<Language> langs = service.findAll();
        List<Book> books = thislang.getBooks(); 
        Book newOne = subservice.findByName(newone);
        Book dumbOne = subservice.findByName(removeone);
        
        if (dumbOne != null)
        {   
            System.out.println("ok");
            books.remove(dumbOne);
            dumbOne.getAuthors().remove(thislang);
            dumbOne.setModifiledDate(new Date());
            subservice.Save(dumbOne);
        }  
        
        if (newOne != null)
        {    
            if (!books.contains(newOne)) 
            {   
                books.add(newOne);
                newOne.getLanguages().add(thislang);
                newOne.setModifiledDate(new Date());
            }
            subservice.Save(newOne);
        }

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
        thislang.setModifiledDate(new Date());
        service.Save(thislang);
    }

    @RequestMapping("Language/delete/{id}")
    public void deleteLanguage(@PathVariable("id") int id)
    {
        if (service.existById(id))
        {
            return;
        }
        Language tmplang = service.findById(id).get();
        List<Book> books = tmplang.getBooks();
        for(Book ele:books)
        {
            ele.getLanguages().remove(tmplang);
        }
        tmplang.setBooks(null);
        subservice.saveAll(books); 
        service.deleteById(id);
    }
}


