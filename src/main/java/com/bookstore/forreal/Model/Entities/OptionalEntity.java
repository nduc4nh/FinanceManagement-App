package com.bookstore.forreal.Model.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bookstore.forreal.Model.Tool.preprocessString;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OptionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    String name;
    @JsonManagedReference
    @ManyToOne
    Book book;
    @JsonManagedReference
    @ManyToOne
    Language lang;
    @OneToOne
    Image img;
    @Column
    Date createdDate;
    @Column
    Date modifiedDate;
    @Column
    long totalprice;

    public OptionalEntity() {
    }

    public OptionalEntity(Book book, Language lang) {
        this.book = book;
        this.lang = lang;
        this.name = "".join("", preprocessString.toArray(this.book.getName()," "))+"VER"+hashName();
        this.totalprice = this.book.getPrice() + this.lang.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(long totalprice) {
        this.totalprice = totalprice;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    public int hashName() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((lang == null) ? 0 : lang.hashCode());
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
        OptionalEntity other = (OptionalEntity) obj;
        if (id != other.id)
            return false;
        return true;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}