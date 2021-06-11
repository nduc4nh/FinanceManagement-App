package com.example.financemanagementapplication.model.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.financemanagementapplication.model.database.entity.parents.Category;

import java.util.Random;

@Entity(tableName = "spending_categories")
public class SpendingCategory extends Category {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public SpendingCategory(String name, String description) {
        super(name, description);
        this.id = new Random().nextLong();
    }

    @Override
    public Long getId() {
        return id;
    }
}
