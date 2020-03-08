package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookService service;
    @GetMapping("/Book/get")
    public List<Book> getAllBook()
    {
        return service.findAll();
    }
    @GetMapping("/Book/get/{id}")
    public Optional<Book> getBookById(@PathVariable("id") int id)
    {
        return service.findById(id);
    }
    @PostMapping("/Book/post")
    public void addBook(@RequestBody Book newBook)
    {
        List<Book> books = service.findAll();
        for (Book ele:books)
        {
            if (ele == newBook) return;
        }
        newBook.setCreatedDate(new Date());
        service.Save(newBook);
    }
    @PutMapping("/Book/put/{id}")
    public void modifyBook(@PathVariable("id") int id, @RequestBody Book modifiedbook)
    {
        if (!service.existById(id))
        {
            return;
        }
        Book thisbook = service.findById(id).get();
        thisbook.setAuthors(modifiedbook.getAuthors());
        thisbook.setGenres(modifiedbook.getGenres());
        thisbook.setLanguages(modifiedbook.getLanguages());
        thisbook.setName(modifiedbook.getName());
        thisbook.setPrice(modifiedbook.getPrice());
        thisbook.setModifiledDate(new Date());

        service.Save(thisbook);
    }
    @DeleteMapping("/Book/delete/{id}")
    public void deleteBook(@PathVariable("id") int id)
    {
        if (!service.existById(id))
        {
            return;
        }
        service.deleteById(id);
    }
}
