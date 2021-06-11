package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String name;
    public Long balance;

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getBalance() {
        return balance;
    }

    public User(String name, Long balance) {
        this.name = name;
        this.balance = balance;
        this.id = new Random().nextLong();
    }

    public void addBalance(Long income) {this.balance += income;}

    public void subBalance(Long expense) {this.balance += expense;}

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
