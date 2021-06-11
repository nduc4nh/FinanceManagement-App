package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.financemanagementapplication.model.database.entity.parents.Category;

import java.util.Random;


@Entity(tableName = "income_categories")
public class IncomeCategory extends Category {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public IncomeCategory(String name, String description) {
        super(name,description);
        this.id = new Random().nextLong();
    }

    @Override
    public Long getId() {
        return id;
    }
}
