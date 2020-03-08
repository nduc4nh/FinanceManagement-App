package com.bookstore.forreal.Model.DataTransfer;

import java.util.List;

public class DTBook {
    private String name;
    private long price;
    private List<String> authorname;
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

    public List<String> getAuthorname() {
        return authorname;
    }

    public void setAuthorname(List<String> authorname) {
        this.authorname = authorname;
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
