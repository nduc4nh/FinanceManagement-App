package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.CategoryAndLimitation;
import com.example.financemanagementapplication.model.database.entity.CategoryAndSpendings;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

@Dao
public interface SpendingCategoryDAO {
    @Query("select * from spending_categories")
    public LiveData<List<SpendingCategory>> findAllSpendingCategory();
    @Query("select * from spending_categories " +
            "where spending_categories.id = :cat_id")
    public LiveData<SpendingCategory> findSpendingCategoryById(Long cat_id);
    @Query("select * from spending_categories " +
            "where spending_categories.name like :name")
    public SpendingCategory findSpendingCategoryByName(String name);

    @Transaction
    @Query("select * from spending_categories")
    public LiveData<List<CategoryAndSpendings>> getSpendingCategorySpendings();

    @Transaction
    @Query("select * from limitations " +
            "where spendingCategoryId = :cateId")
    public LiveData<Limitation> getLimitationsForCategory(Long cateId);

    @Transaction
    @Query("select * from spending_categories")
    public LiveData<CategoryAndLimitation> getSpendingCategoryLimitation();

    @Transaction
    @Query("select * from spending_categories")
    public LiveData<List<CategoryAndSpendings>> getSpendingCategoryPeriods();

    @Insert
    public void addSpendingCategory(SpendingCategory spending);
    @Update
    public void updateSpendingCategory(SpendingCategory spending);
    @Delete
    public void deleteSpendingCategories(List<SpendingCategory> spendingCategories);
    @Delete
    public void deleteSpendingCategory(SpendingCategory spending);
}
