package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAndIncomes {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    public List<Income> incomes;
}
