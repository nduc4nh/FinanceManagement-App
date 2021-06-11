package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

@Dao
public interface PeriodPaymentDAO {
    @Query("select * from period_payments")
    public LiveData<List<PeriodPayment>> findAllPeriodPayment();
    @Query("select * from period_payments " +
            "where period_payments.id = :period_payment_id")
    public LiveData<PeriodPayment> findPeriodPaymentById(Long period_payment_id);
    @Query("select * from period_payments " +
            "where period_payments.name like :name")
    public PeriodPayment findPeriodPaymentByName(String name);

    @Transaction
    @Query("select b.* from spending_categories b " +
            "where b.id = :id")
    public LiveData<SpendingCategory> getPeriodPaymentSpendingCategory(Long id);

    @Insert
    public void addPeriodPayment(PeriodPayment period_payment);
    @Update
    public void updatePeriodPayment(PeriodPayment period_payment);
    @Delete
    public void deletePeriodPayments(List<PeriodPayment> period_payments);
    @Delete
    public void deletePeriodPayment(PeriodPayment period_payment);
}
