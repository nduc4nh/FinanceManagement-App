package com.bookstore.forreal.Controller;

import com.bookstore.forreal.Model.Entities.Author;
import com.bookstore.forreal.Model.Entities.Book;
import com.bookstore.forreal.Model.Entities.Genre;
import com.bookstore.forreal.Model.Entities.Language;
import com.bookstore.forreal.Model.Services.AuthorService;
import com.bookstore.forreal.Model.Services.BookService;
import com.bookstore.forreal.Model.Services.GenreService;
import com.bookstore.forreal.Model.Services.LanguageService;
import com.bookstore.forreal.Model.Tool.preprocessString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookService service;
    @Autowired
    AuthorService subserviceAu;
    @Autowired
    LanguageService subserviceLa;
    @Autowired
    GenreService subserviceGe;


    @GetMapping("GET/Book")
    public List<Book> getAllBook()
    {
        return service.findAll();
    }
    @GetMapping("GET/Book/{id}")
    public Optional<Book> getBookById(@PathVariable("id") int id)
    {
        return service.findById(id);
    }
    @PostMapping("POST/Book")
    public void addBook(@RequestParam("name") String name,@RequestParam("authors") String authors,@RequestParam("price") String price,@RequestParam("genre") String genres,@Nullable@RequestParam("lang") String langs)
    {
        List<Book> books = service.findAll();
        for (Book ele:books)
        {
            if (ele.getName().equals(name)) return;
        }
        //Adding authors
        ArrayList<Author> authorlist = (ArrayList<Author>) subserviceAu.findAllByName(authors.strip().split(","));
        Book tmp = new Book(name,Long.parseLong(preprocessString.doString(price)));
        
        for (Author ele:authorlist)
        {
            ele.getBooks().add(tmp);
            subserviceAu.Save(ele);
        }
        tmp.setAuthors(authorlist);
        //Adding genre
        ArrayList<Genre> genrelist = (ArrayList<Genre>) subserviceGe.findAllByName(genres.strip().split(","));
        for (Genre ele:genrelist)  
        {
            ele.getBooks().add(tmp);
            subserviceGe.Save(ele);
        }
        tmp.setGenres(genrelist);
        //Adding language
        ArrayList<Language> langlist = (ArrayList<Language>) subserviceLa.findAllByName(langs.strip().split(","));
        for (Language ele:langlist)
        {
            ele.getBooks().add(tmp);
            subserviceLa.Save(ele);
        }
        tmp.setLanguages(langlist);
        
        tmp.setCreatedDate(new Date());
        service.Save(tmp);
    }
    
    @PutMapping("PUT/Book/{id}")
    public void modifyBook(@PathVariable("id") int id,@Nullable@RequestParam("name")String name,@Nullable@RequestParam("price") String price,
    @Nullable@RequestParam("newgenre") String newgenre,@Nullable@RequestParam("removegenre") String removegenre,
    @Nullable@RequestParam("newauthor") String newauthor,@Nullable@RequestParam("removeauthor")  String removeauthor,
    @Nullable@RequestParam("newlang")  String newlang,@Nullable@RequestParam("removelang")  String removelang)
    {
        if (!service.existById(id))
        {
            return;
        }
        Book thisbook = service.findById(id).get();
        List<Book> books = service.findAll();
        
        //modifying name below 
        if (name!=null) 
        {
            for (Book ele:books)
            {
                String tmp = ele.getName();
                if (preprocessString.doString(tmp) == preprocessString.doString(name)
                )
                {
                    name = ele.getName();
                }
            }
            thisbook.setName(name);
        }
        
        //modifyin price below 
        try{
            long newprice = Long.parseLong(preprocessString.doString(price));
        if (newprice >= 0) thisbook.setPrice(newprice);
        }catch(Exception e){}
        
        //GENRE_ADD_AND_REMOVE
        bindingAction bookAndgenre = new bindingAction<Book,Genre,BookService,GenreService>();
        if (newgenre != null) bookAndgenre.addToSource(service, subserviceGe, thisbook, newgenre, books);
        if (removegenre != null) bookAndgenre.removeFromSource(service, subserviceGe, thisbook, removegenre, books);

        bindingAction bookAndauthor = new bindingAction<Book,Author,BookService,AuthorService>();
        if (newauthor != null) bookAndauthor.addToSource(service, subserviceAu, thisbook, newauthor, books);
        if (removeauthor != null) bookAndauthor.removeFromSource(service, subserviceAu, thisbook, removeauthor, books);

        //LANGUAGE_ADD_AND_REMOVE
        bindingAction bookAndlang = new bindingAction<Book,Language,BookService,LanguageService>();
        if (newlang != null) bookAndlang.addToSource(service, subserviceLa, thisbook, newlang, books);
        if (removelang != null) bookAndlang.removeFromSource(service, subserviceLa, thisbook, removelang, books);
        
        thisbook.setModifiledDate(new Date());
        service.Save(thisbook);
        
    }
    @DeleteMapping("DELETE/Book/{id}")
    public void deleteBook(@PathVariable("id") int id)
    {
        if (!service.existById(id))
        {
            return;
        }
        Book tmpbook = service.findById(id).get();
        List<Author> authors = tmpbook.getAuthors();
        for(Author ele:authors)
        {
            ele.getBooks().remove(tmpbook);
        }
        tmpbook.setAuthors(null);
        subserviceAu.saveAll(authors);
        service.deleteById(id);
    }
}
