package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CategoryAndLimitation {
    @Embedded public SpendingCategory category;
    @Relation(
            parentColumn = "id",
            entityColumn = "spendingCategoryId"
    )
    public Limitation limitation;
}
