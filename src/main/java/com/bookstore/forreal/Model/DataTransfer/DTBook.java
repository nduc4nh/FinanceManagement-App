package com.bookstore.forreal.Model.DataTransfer;

import java.util.ArrayList;


public class DTBook {
    private String name;
    private long price;
    private ArrayList<DTAuthor> authors;
    private String language;
    private String genre;
    

    public DTBook(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public DTBook() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public ArrayList<DTAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<DTAuthor> authors) {
        this.authors = authors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }    
}
