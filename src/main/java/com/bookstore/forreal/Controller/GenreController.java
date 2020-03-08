package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {
    @Autowired
    GenreService service;
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
    public void addGenre(@RequestBody Genre newgenre)
    {
        List<Genre> genres = service.findAll();
        for (Genre ele:genres)
        {
            if (ele == newgenre) return;
        }
        newgenre.setCreatedDate(new Date());
        service.Save(newgenre);
    }
    @PutMapping("/Genre/put/{id}")
    public void modifyGenre(@PathVariable("id") int id,@RequestBody Genre modifiedgenre)
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
    }
    @DeleteMapping("/Genre/delete/{id}")
    public void deleteGenre(@PathVariable("id") int id)
    {
        if (!service.existById(id))
        {
            return;
        }
        service.deleteById(id);
    }
}
