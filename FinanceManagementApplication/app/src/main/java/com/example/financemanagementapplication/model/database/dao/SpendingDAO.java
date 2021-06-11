package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.User;

import java.util.List;


@Dao
public interface SpendingDAO {
    @Query("select * from spendings")
    public LiveData<List<Spending>> findAllSpendings();
    @Query("select * from spendings " +
            "where spendings.id = :spending_id")
    public LiveData<Spending> findSpendingById(Long spending_id);
    @Query("select * from spendings " +
            "where spendings.name like :name")
    public Spending findSpendingByName(String name);

    @Query("select * from spendings " +
            "where spendings.date = :date")
    public LiveData<List<Spending>> findSpendingByDate(String date);

    @Transaction
    @Query("select * from users " +
            "where users.id = :userId")
    public LiveData<User> findSpendingUser(Long userId);

    @Transaction
    @Query("select a.* from spending_categories as a " +
            "where a.id = :cateId")
    public LiveData<SpendingCategory> findSpendingCategory(Long cateId);

    @Insert
    public void addSpending(Spending spending);
    @Update
    public void updateSpending(Spending spending);
    @Delete
    public void deleteSpendings(List<Spending> spendings);
    @Delete
    public void deleteSpending(Spending spending);

    @Query("select sum(amount) from spendings " +
            "where date like :date")
    public LiveData<Long> totalByDate(String date);

    @Query("select sum(amount) from spendings " +
            "where date like :date and categoryId = :cateid")
    public LiveData<Long> totalByDateAndCate(String date,Long cateid);

    @Query("select * from spendings " +
            "where date like :date")
    public LiveData<List<Spending>> getSpendingByDateAlike(String date);
}
