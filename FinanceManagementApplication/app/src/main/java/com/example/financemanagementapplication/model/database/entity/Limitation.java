package com.example.financemanagementapplication.model.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "limitations")
public class Limitation {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String name;
    public Long current;
    public Long max;
    public Long spendingCategoryId;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCurrent() {
        return current;
    }

    public Long getMax() {
        return max;
    }

    public Long getSpendingCategoryId() {
        return spendingCategoryId;
    }


    public Limitation(Long id,String name, Long current, Long max, Long spendingCategoryId) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.max = max;
        this.spendingCategoryId = spendingCategoryId;
    }

    public String getPercentage()
    {
        return String.format("%.1f %%",(double)current/max*100);
    }

    public Boolean isCritical()
    {
        return (this.current > this.max);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public void setSpendingCategoryId(Long spendingCategoryId) {
        this.spendingCategoryId = spendingCategoryId;
    }

    public Limitation copy()
    {
        return new Limitation(this.id,this.name,this.current, this.max,this.spendingCategoryId);
    }
}
