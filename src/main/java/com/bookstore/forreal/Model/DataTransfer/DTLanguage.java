package com.bookstore.forreal.Model.DataTransfer;

import java.util.ArrayList;

public class DTLanguage {
    private String name;
    private ArrayList<DTBook> books;

    public DTLanguage(String name) {
        this.name = name;
    }

    public DTLanguage() {
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
