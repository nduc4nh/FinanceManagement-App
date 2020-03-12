package com.bookstore.forreal.Model.DataTransfer;

import java.util.ArrayList;

public class DTAuthor {
    private String name;
    
    private ArrayList<DTBook> books;
    
    public DTAuthor(String name) {
        this.name = name;
    }

    DTAuthor(){}
    
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
