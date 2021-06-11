package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.CategoryAndIncomes;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;

import java.util.List;

@Dao
public interface IncomeCategoryDAO {
    @Query("select * from income_categories")
    public LiveData<List<IncomeCategory>> findAllIncomeCategory();
    @Query("select * from income_categories " +
            "where income_categories.id = :cat_id")
    public LiveData<IncomeCategory> findIncomeCategoryById(Long cat_id);
    @Query("select * from income_categories " +
            "where income_categories.name like :name")
    public IncomeCategory findIncomeCategoryByName(String name);

    @Transaction
    @Query("select * from income_categories")
    public LiveData<List<CategoryAndIncomes>> getIncomeCategoryIncomes();

    @Insert
    public void addIncomeCategory(IncomeCategory user);
    @Update
    public void updateIncomeCategory(IncomeCategory user);
    @Delete
    public void deleteIncomeCategories(List<IncomeCategory> income_categories);
    @Delete
    public void deleteIncomeCategory(IncomeCategory user);
}
