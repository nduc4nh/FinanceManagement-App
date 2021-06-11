package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.User;

import java.util.List;

@Dao
public interface IncomeDAO {
    @Query("select * from incomes")
    public LiveData<List<Income>> findAllIncomes();
    @Query("select * from incomes " +
            "where incomes.id = :income_id")
    public LiveData<Income> findIncomeById(Long income_id);
    @Query("select * from incomes " +
            "where incomes.name like :name")
    public Income findIncomeByName(String name);

    @Query("select * from incomes " +
            "where incomes.date = :date")
    public LiveData<List<Income>> findIncomeByDate(String date);
    @Transaction
    @Query("select a.* from users a " +
            "where a.id = :userId")
    public LiveData<User> findIncomeUser(Long userId);

    @Transaction
    @Query("select a.* from income_categories a " +
            "where a.id = :cateId")
    public LiveData<IncomeCategory> findIncomeCategory(Long cateId);

    @Insert
    public void addIncome(Income income);
    @Update
    public void updateIncome(Income income);
    @Delete
    public void deleteIncomes(List<Income> incomes);
    @Delete
    public void deleteIncome(Income income);

    @Query("select sum(amount) from incomes " +
            "where date like :date")
    public LiveData<Long> totalByDate(String date);

    @Query("select sum(amount) from incomes " +
            "where date like :date and categoryId = :cateid")
    public LiveData<Long> totalByDateAndCate(String date,Long cateid);

    @Query("select * from incomes " +
            "where date like :date")
    public LiveData<List<Income>> getIncomeByDateAlike(String date);
}
