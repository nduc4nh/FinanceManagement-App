package com.bookstore.forreal.Model.DataTransfer;

import java.util.ArrayList;

public class DTGenre {
    public String name;
    public ArrayList<DTBook> books;
    
    public DTGenre(String name) {
        this.name = name;
    }

    public DTGenre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DTBook> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<DTBook> books) {
        this.books = books;
    }
}
