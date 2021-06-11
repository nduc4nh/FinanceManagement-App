package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndSpendings {
    @Embedded
    public SpendingCategory category;
    @Relation(parentColumn = "id",
            entityColumn = "categoryId")
    public List<Spending> incomes;
}
