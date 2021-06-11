package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

@Dao
public interface LimitationDAO {
    @Query("select * from limitations")
    public LiveData<List<Limitation>> findAllLimitation();
    @Query("select * from limitations " +
            "where limitations.id = :limitation_id")
    public LiveData<Limitation> findLimitationById(Long limitation_id);
    @Query("select * from limitations " +
            "where limitations.name like :name")
    public Limitation findLimitationByName(String name);

    @Transaction
    @Query("select * from spending_categories as a " +
            "where a.id = :id")
    public LiveData<SpendingCategory> getLimitationCategory(Long id);

    @Transaction
    @Query("select a.* from limitations a,spending_categories b " +
            "where a.spendingCategoryId = :cateId")
    public LiveData<Limitation> getLimitationWithCategory(Long cateId);

    @Insert
    public void addLimitation(Limitation limitation);
    @Update
    public void updateLimitation(Limitation limitation);
    @Delete
    public void deleteLimitations(List<Limitation> limitations);
    @Delete
    public void deleteLimitation(Limitation limitation);

    @Transaction
    @Query("select a.* from limitations a,spending_categories b " +
            "where a.spendingCategoryId = :cateId")
    public Limitation getLimitationWithCategoryRaw(Long cateId);
}
