package com.example.financemanagementapplication.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.SpendingDAO;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.User;

import java.util.List;

public class SpendingRepository {
    private SpendingDAO spendingDAO;
    private LiveData<List<Spending>> spendings;
    private LiveData<Spending> spending;
    private LiveData<SpendingCategory> category;
    private LiveData<User> user;

    public SpendingRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        spendingDAO = db.getSpendingDAO();
        spendings = spendingDAO.findAllSpendings();
    }

    public LiveData<Spending> getSpending(Long id) {
        this.spending = spendingDAO.findSpendingById(id);
        return spending;
    }

    public LiveData<List<Spending>> getSpendingByDate(String date)
    {
        return spendingDAO.findSpendingByDate(date);
    }

    public LiveData<SpendingCategory> getCategory() {
        return category;
    }

    public LiveData<SpendingCategory> getCategory(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
           this.category = spendingDAO.findSpendingCategory(id);
        });
        return category;
    }

    public LiveData<User> getUser() {
        return user;
    }
    public LiveData<List<Spending>> getAllSpendings()
    {
        return spendings;
    }

    public LiveData<User> getUser(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.user = spendingDAO.findSpendingUser(id);
        });
        return user;
    }

    public void insertSpending(Spending spending)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDAO.addSpending(spending);
        });
    }
    public void updateSpending(Spending spending)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDAO.updateSpending(spending);
        });
    }
    public void deleteSpending(Spending spending)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDAO.deleteSpending(spending);
        });
    }

    public LiveData<Long> totalByDate(String date)
    {
        return spendingDAO.totalByDate(date);
    }

    public LiveData<Long> totalByDateAndCate(String date,Long cateid){
        return spendingDAO.totalByDateAndCate(date,cateid);
    }

    public LiveData<List<Spending>> getSpendingByDateAlike(String date){
        return spendingDAO.getSpendingByDateAlike(date);
    }
}
