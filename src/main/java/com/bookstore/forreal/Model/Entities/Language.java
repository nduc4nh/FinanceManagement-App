package com.bookstore.forreal.Model.Entities;

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
    @ManyToMany(mappedBy = "languages")
    @JsonBackReference
    List<Book> books;
    @Column
    Date createdDate;

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

    @Column
    Date modifiledDate;

    public Language() {
    }
    public Language(String name) {
        this.name = name;
        this.books = null;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                '}';
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
}
