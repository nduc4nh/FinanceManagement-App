package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.GenreService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

    @GetMapping("GET/Genre")
    public List<Genre> getAllGenre()
    {
        return service.findAll();
    }
    @GetMapping("GET/Genre/{id}")
    public Optional<Genre> getGenreById(@PathVariable("id") int id)
    {
        if (!service.existById(id)) return null;
        return service.findById(id);
    }
    @PostMapping("POST/Genre")
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
    @PutMapping("PUT/Genre/{id}")

    public void modifyGenre(@PathVariable("id") int id,@Nullable@RequestParam("name") String name,@Nullable@RequestParam("newbook") String newone,@Nullable@RequestParam("removebook") String removeone)
    {
        if (!service.existById(id))
        {
            return;
        }
        Genre thisgenre = service.findById(id).get();
        List<Genre> genres = service.findAll();

        bindingAction genreAndbook = new bindingAction<Genre,Book,GenreService,BookService>();
        if (newone != null) genreAndbook.addToSource(service, subservice, thisgenre, newone, genres);
        if (removeone != null) genreAndbook.removeFromSource(service, subservice, thisgenre, removeone, genres);
        if (name != null)
        {
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
        }
        thisgenre.setModifiledDate(new Date());
        service.Save(thisgenre);
    }
    @DeleteMapping("DELETE/Genre/{id}")
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
