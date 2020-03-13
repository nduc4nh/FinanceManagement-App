package com.bookstore.forreal.Model.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String imgpath;
    @ManyToOne
    Book book;
    @ManyToOne
    Language lang;
    @OneToOne
    OptionalEntity optionalbook;

    public Image(){};
    public Image(String imgpath, Book book) {
        this.name = "IMG"+hashName();
        this.imgpath = imgpath;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public OptionalEntity getOptionalbook() {
        return optionalbook;
    }

    public void setOptionalbook(OptionalEntity optionalbook) {
        this.optionalbook = optionalbook;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public int hashName() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imgpath == null) ? 0 : imgpath.hashCode());
        return result;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }
}