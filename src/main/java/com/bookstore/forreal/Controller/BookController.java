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
    public void addBook(@RequestParam("name") String name,@RequestParam("authors") String authors,@RequestParam("price") long price,@RequestParam("genre") String genres,@RequestParam("lang") String langs)
    {
        List<Book> books = service.findAll();
        for (Book ele:books)
        {
            if (ele.getName().equals(name)) return;
        }
        //Adding authors
        ArrayList<Author> authorlist = (ArrayList<Author>) subserviceAu.findAllByName(authors.strip().split(","));
        Book tmp = new Book(name,price);
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
    
    @PutMapping("/Book/put/{id}")
    /*public void modifyBook(@PathVariable("id") int id, @RequestBody Book modifiedbook)
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
    }*/
    public void modifyBook(@PathVariable("id") int id,@RequestParam("name")String name,@RequestParam("price") long newprice,
    @RequestParam("newgenre") String newgenre,@RequestParam("removegenre") String removegenre,
    @RequestParam("newauthor") String newauthor,@RequestParam("removeauthor")  String removeauthor,
    @RequestParam("newlang")  String newlang,@RequestParam("removelang")  String removelang)
    {
        if (!service.existById(id))
        {
            return;
        }
        Book thisbook = service.findById(id).get();
        List<Book> books = service.findAll();
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
        if (newprice > 0) thisbook.setPrice(newprice);

        //GENRE_ADD_AND_REMOVE
        List<Genre> genres = thisbook.getGenres(); 
        Genre newGenre = subserviceGe.findByName(newgenre);
        Genre dumbGenre = subserviceGe.findByName(removegenre);
        
        if (dumbGenre != null)
        {   
            System.out.println("ok");
            genres.remove(dumbGenre);
            dumbGenre.getBooks().remove(thisbook);
            dumbGenre.setModifiledDate(new Date());
            subserviceGe.Save(dumbGenre);
        }  
        
        if (newGenre != null)
        {    
            if (!books.contains(newGenre)) 
            {   
                genres.add(newGenre);
                newGenre.getBooks().add(thisbook);
                newGenre.setModifiledDate(new Date());
            }
            subserviceGe.Save(newGenre);
        }
        //AUTHOR_ADD_AND_REMOVE
        List<Author> authors = thisbook.getAuthors(); 
        Author newAuthor = subserviceAu.findByName(newauthor);
        Author dumbAuthor = subserviceAu.findByName(removeauthor);
        
        if (dumbAuthor != null)
        {   
            System.out.println("ok");
            authors.remove(dumbAuthor);
            dumbAuthor.getBooks().remove(thisbook);
            dumbAuthor.setModifiledDate(new Date());
            subserviceAu.Save(dumbAuthor);
        }  
        
        if (newAuthor != null)
        {    
            if (!books.contains(newAuthor)) 
            {   
                authors.add(newAuthor);
                newAuthor.getBooks().add(thisbook);
                newAuthor.setModifiledDate(new Date());
            }
            subserviceAu.Save(newAuthor);
        }

        //LANGUAGE_ADD_AND_REMOVE
        List<Language> langs = thisbook.getLanguages(); 
        Language newLang = subserviceLa.findByName(newlang);
        Language dumbLang = subserviceLa.findByName(removelang);
        
        if (dumbLang != null)
        {   
            System.out.println("ok");
            langs.remove(dumbLang);
            dumbLang.getBooks().remove(thisbook);
            dumbLang.setModifiledDate(new Date());
            subserviceLa.Save(dumbLang);
        }  
        
        if (newLang != null)
        {    
            if (!books.contains(newLang)) 
            {   
                langs.add(newLang);
                newLang.getBooks().add(thisbook);
                newLang.setModifiledDate(new Date());
            }
            subserviceLa.Save(newLang);
        }
        
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
