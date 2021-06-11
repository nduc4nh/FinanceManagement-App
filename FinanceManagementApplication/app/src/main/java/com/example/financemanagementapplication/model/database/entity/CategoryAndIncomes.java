package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndIncomes {
    @Embedded public IncomeCategory category;
    @Relation(parentColumn = "id",
            entityColumn = "categoryId")
    public List<Income> incomes;

    public IncomeCategory getCategory() {
        return category;
    }

    public List<Income> getIncomes() {
        return incomes;
    }
}
