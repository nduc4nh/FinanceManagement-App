package com.bookstore.forreal.Model.Entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    int AuthorId;
    @Column(nullable = false)
    String name;
    //@Column(nullable = false)
    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    List<Book> books;
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

    public Author() {
    }

    public Author(String name)
    {
        this.name = name;
        this.books = null;
    }

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
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

    public void setBooks(List<Book> list) {
        this.books = list;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(getName(), author.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorId());
    }
}
