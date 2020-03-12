package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Services.AuthorService;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.GenreService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class AuthorController {
    @Autowired
    AuthorService service;
    @Autowired
    BookService subservice;
    @Autowired
    GenreService s;
    
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

    @PutMapping("Author/put/{id}")
    /*public void modifyAuthor(@PathVariable("id") int id,@RequestBody Author newform)
    
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
    }*/
    public void modifyAuthor(@PathVariable("id") int id,@RequestParam("name") String name,@RequestParam("new") String newBook,@RequestParam("remove") String book2remove)
    {
        if (!service.existById(id))
        {
            return;
        }
        Author thisguy = service.findById(id).get();
        List<Author> authors = service.findAll();
        List<Book> books = thisguy.getBooks(); 
        Book newOne = subservice.findByName(newBook);
        Book dumbOne = subservice.findByName(book2remove);
        
        if (dumbOne != null)
        {   
            System.out.println("ok");
            books.remove(dumbOne);
            dumbOne.getAuthors().remove(thisguy);
            dumbOne.setModifiledDate(new Date());
            subservice.Save(dumbOne);
        }  
        
        if (newOne != null)
        {    
            if (!books.contains(newOne)) 
            {   
                books.add(newOne);
                newOne.getAuthors().add(thisguy);
                newOne.setModifiledDate(new Date());
            }
            subservice.Save(newOne);
        }

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
        thisguy.setModifiledDate(new Date());
        service.Save(thisguy);
    }

    @DeleteMapping("Author/delete/{id}")
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
