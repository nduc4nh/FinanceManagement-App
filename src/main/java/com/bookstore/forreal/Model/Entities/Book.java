package com.bookstore.forreal.Model.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bookId;
    @Column(nullable = false)
    String name;
    //@Column(nullable = false)
    @ManyToMany
    @JsonManagedReference
    List<Genre> genres;
    //@Column(nullable = false)
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Author> authors;
    //@Column(nullable = false)
    @ManyToMany
    @JsonManagedReference
    List<Language> languages;
    @Column(nullable = false)
    long price;
    @Column
    Date createdDate;
    @Column
    Date modifiledDate;


    public Book() {
    }

    public  Book(String name,long price)
    {
        this.name = name;
        this.price = price;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiledDate() {
        return modifiledDate;
    }

    public void setModifiledDate(Date modifiledDate) {
        this.modifiledDate = modifiledDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getName(), book.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId());
    }
}
