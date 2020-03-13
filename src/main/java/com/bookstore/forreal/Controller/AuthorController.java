package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Services.AuthorService;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.GenreService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
public class AuthorController {
    @Autowired
    AuthorService service;
    @Autowired
    BookService subservice;
    @Autowired
    GenreService s;
    
    @GetMapping("GET/Author")
    public List<Author> getAllAuthor()
    {
        
        return service.findAll();
    }
    @GetMapping("GET/Author/{id}")
    public Optional<Author> getAuthorById(@PathVariable("id") Integer id)
    {
        return service.findById(id);
    }
    

    @PostMapping ("POST/Author")
    public void addAuthor(@RequestParam("name") String name)
    {
        List<Author> tmpAuthors = service.findAll();
        for (Author ele:tmpAuthors) {
            if (ele.getName() == name) return;
        }
        Author tmp = new Author(name);
        //System.out.println(books.toString());
        tmp.setAuthorId(tmp.hashCode());
        tmp.setCreatedDate(new Date());
        System.out.print(tmp.getBooks());
        service.Save(tmp);
    }

    @PutMapping("PUT/Author/{id}")
    public void modifyAuthor(@PathVariable("id") int id,@Nullable@RequestParam("name") String name,@Nullable@RequestParam("newbook") String newbook,@Nullable@RequestParam("removebook") String removebook)
    {
        if (!service.existById(id))
        {
            return;
        }
        Author thisguy = service.findById(id).get();
        List<Author> authors = service.findAll();

        bindingAction authorAndbook = new bindingAction<Author,Book,AuthorService,BookService>();
        if (newbook != null) authorAndbook.addToSource(service, subservice, thisguy, newbook, authors);
        if (removebook != null) authorAndbook.removeFromSource(service, subservice, thisguy, removebook, authors);
        if (name!= null)
        {
            for (Author ele:authors)
            {
                String tmp = ele.getName();
                if (preprocessString.doString(tmp) == preprocessString.doString(name)
                )
                {
                    name = ele.getName();
                    break;
                }
            }
            thisguy.setName(name);
        }
        thisguy.setModifiledDate(new Date());
        service.Save(thisguy);
    }

    @DeleteMapping("DELETE/Author/{id}")
    public void deleteAuthor(@PathVariable("id") int id)
    {
        if (!service.existById(id))
        {
            return;
        }
        Author tmpauthor = service.findById(id).get();
        List<Book> books = tmpauthor.getBooks();
        for(Book ele:books)
        {
            ele.getAuthors().remove(tmpauthor);
        }
        tmpauthor.setBooks(null);
        subservice.saveAll(books);
        service.deleteById(id);
    }

    @RequestMapping(value="Author/test", method=RequestMethod.GET)
    public List<Genre> testing (@RequestParam("name") String name)
    {
        return s.findAllByName(name.strip().split(","));
    
    }

}
