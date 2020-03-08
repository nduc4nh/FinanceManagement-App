package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    @Autowired
    AuthorService service;
    @GetMapping("Author/get")
    public List<Author> getAllAuthor()
    {
        return service.findAll();
    }
    @GetMapping("Author/get/{id}")
    public Optional<Author> getAuthorById(@PathVariable("id") Integer id)
    {
        return service.findById(id);
    }

    @PostMapping ("Author/post")
    public void addAuthor(@RequestBody Author newAuthor)
    {
        List<Author> tmpAuthors = service.findAll();
        for (Author ele:tmpAuthors) {
            if (ele == newAuthor) return;
        }
        newAuthor.setAuthorId(newAuthor.hashCode());
        newAuthor.setCreatedDate(new Date());
        service.Save(newAuthor);
    }

    @RequestMapping("Author/put/{id}")
    public void modifyAuthor(@PathVariable("id") int id,@RequestBody Author newform)
    {
        if (!service.existById(id))
        {
            return;
        }
        Author thisguy = service.findById(id).get();
        thisguy.setName(newform.getName());
        thisguy.setBooks(newform.getBooks());
        thisguy.setModifiledDate(new Date());
        service.Save(thisguy);
    }

    @RequestMapping("Author/delete/{id}")
    public void deleteAuthor(@PathVariable("id") int id)
    {
        if (service.existById(id))
        {
            return;
        }
        service.deleteById(id);
    }
}
