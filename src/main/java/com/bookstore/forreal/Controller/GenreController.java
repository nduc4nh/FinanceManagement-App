package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.GenreService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {
    @Autowired
    GenreService service;
    @Autowired
    BookService subservice;

    @GetMapping("/Genre/get")
    public List<Genre> getAllGenre()
    {
        return service.findAll();
    }
    @GetMapping("/Genre/get/{id}")
    public Optional<Genre> getGenreById(@PathVariable("id") int id)
    {
        if (!service.existById(id)) return null;
        return service.findById(id);
    }
    @PostMapping("/Genre/post")
    public void addGenre(@RequestParam("name") String name)
    {
        List<Genre> genres = service.findAll();
        for (Genre ele:genres)
        {
            if (ele.getName().equals(name)) return;
        }
        Genre newgenre = new Genre(name);
        newgenre.setCreatedDate(new Date());
        service.Save(newgenre);
    }
    @PutMapping("/Genre/put/{id}")
    /*public void modifyGenre(@PathVariable("id") int id,@RequestBody Genre modifiedgenre)
    {
        if (!service.existById(id))
        {
            return;
        }
        Genre thisgenre = service.findById(id).get();
        thisgenre.setBooks(modifiedgenre.getBooks());
        thisgenre.setName(modifiedgenre.getName());
        thisgenre.setModifiledDate(new Date());
        service.Save(thisgenre);
    }*/
    public void modifyGenre(@PathVariable("id") int id,@RequestParam("name") String name,@RequestParam("new") String newone,@RequestParam("remove") String removeone)
    {
        if (!service.existById(id))
        {
            return;
        }
        Genre thisgenre = service.findById(id).get();
        List<Genre> genres = service.findAll();
        List<Book> books = thisgenre.getBooks(); 
        Book newOne = subservice.findByName(newone);
        Book dumbOne = subservice.findByName(removeone);
        
        if (dumbOne != null)
        {   
            System.out.println("ok");
            books.remove(dumbOne);
            dumbOne.getAuthors().remove(thisgenre);
            dumbOne.setModifiledDate(new Date());
            subservice.Save(dumbOne);
        }  
        
        if (newOne != null)
        {    
            if (!books.contains(newOne)) 
            {   
                books.add(newOne);
                newOne.getGenres().add(thisgenre);
                newOne.setModifiledDate(new Date());
            }
            subservice.Save(newOne);
        }

        for (Genre ele:genres)
        {
            String tmp = ele.getName();
            if (preprocessString.doString(tmp) == preprocessString.doString(name)
            )
            {
                name = ele.getName();
                break;
            }
        }
        thisgenre.setName(name);
        thisgenre.setModifiledDate(new Date());
        service.Save(thisgenre);
    }
    @DeleteMapping("/Genre/delete/{id}")
    public void deleteGenre(@PathVariable("id") int id)
    {
        if (!service.existById(id))
        {
            return;
        }
        Genre tmpgenre = service.findById(id).get();
        List<Book> books = tmpgenre.getBooks();
        for(Book ele:books)
        {
            ele.getGenres().remove(tmpgenre);
        }
        tmpgenre.setBooks(null);
        subservice.saveAll(books); 
        service.deleteById(id);
    }
}
