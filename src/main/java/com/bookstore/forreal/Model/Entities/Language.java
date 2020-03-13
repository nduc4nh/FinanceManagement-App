package com.bookstore.forreal.Model.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int langId;
    @Column(nullable = false)
    String name;
    //@Column(nullable = false)
    @JsonBackReference
    @OneToMany(mappedBy = "lang")
    List<OptionalEntity> optionalbook;
    @OneToMany(mappedBy = "lang")
    List<Image> images;
    @JsonBackReference
    @ManyToMany(mappedBy = "languages")
    List<Book> books;
    @Column
    long price;
    @Column
    Date createdDate;
    @Column
    Date modifiledDate;

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

    
    public Language() {
    }
    public Language(String name) {
        this.name = name;
        this.images = (List<Image>) new ArrayList<Image>();
        this.optionalbook = (List<OptionalEntity>) new ArrayList<OptionalEntity>();
    }


    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                '}';
    }

    public List<OptionalEntity> getOptionalbook() {
        return optionalbook;
    }

    public void setOptionalbook(List<OptionalEntity> optionalbook) {
        this.optionalbook = optionalbook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;
        Language language = (Language) o;
        return Objects.equals(getName(), language.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLangId());
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
