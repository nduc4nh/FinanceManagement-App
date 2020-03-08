package com.bookstore.forreal.Controller;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class LanguageController {
    @Autowired
    LanguageService service;
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
    public void addLanguage(@RequestBody Language newLanguage)
    {
        List<Language> tmpLanguages = service.findAll();
        for (Language ele:tmpLanguages) {
            if (ele == newLanguage) return;
        }

        newLanguage.setCreatedDate(new Date());
        service.Save(newLanguage);
    }

    @RequestMapping("Language/put/{id}")
    public void modifyLanguage(@PathVariable("id") int id,@RequestBody Language newform)
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
    }

    @RequestMapping("Language/delete/{id}")
    public void deleteLanguage(@PathVariable("id") int id)
    {
        if (service.existById(id))
        {
            return;
        }
        service.deleteById(id);
    }
}


