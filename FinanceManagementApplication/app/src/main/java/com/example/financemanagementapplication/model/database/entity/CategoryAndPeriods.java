package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndPeriods {
    @Embedded public SpendingCategory category;
    @Relation(
            parentColumn = "id",
            entityColumn = "spendingCategoryId"
    )
    public List<PeriodPayment> periodPayment;
}
