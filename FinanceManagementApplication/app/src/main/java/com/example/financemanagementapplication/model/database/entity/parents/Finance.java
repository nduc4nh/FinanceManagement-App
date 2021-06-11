package com.example.financemanagementapplication.model.database.entity.parents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finance {
    public String name;
    public String date;
    public Long amount;
    public Long userId;
    public Long categoryId;
    public String type;

    public Finance(String name, Long amount, Long userId, Long categoryId) {
        this.name = name;
        this.amount = amount;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId(){return 0l;}

    @Override
    public String toString() {
        return "Finance{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
