package com.example.financemanagementapplication.model.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "period_payments")
public class PeriodPayment {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String name;
    public Long amount;
    public String type; // deprecated
    public String createdOn;
    public int currentPeriod;

    public String getCreatedOn() {
        return createdOn;
    }

    public PeriodPayment(String name, Long amount, int currentPeriod, Long spendingCategoryId, String createdOn) {
        this.id = new Random().nextLong();
        this.name = name;
        this.amount = amount;
        this.currentPeriod = currentPeriod;
        this.spendingCategoryId = spendingCategoryId;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public Long getSpendingCategoryId() {
        return spendingCategoryId;
    }

    public Long spendingCategoryId;

    public String getState(int curMonth)
    {
        if (curMonth == this.currentPeriod )
        {
            return "Status: Unpaid";
        }
        else {
            return "Status: Paid";
        }
    }
}
