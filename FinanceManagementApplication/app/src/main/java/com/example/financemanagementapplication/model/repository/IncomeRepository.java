package com.example.financemanagementapplication.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.IncomeDAO;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.User;

import java.util.List;

public class IncomeRepository {
    private IncomeDAO incomeDAO;
    private LiveData<Income> income;
    private LiveData<List<Income>> incomes;
    private LiveData<IncomeCategory> category;
    private LiveData<User> user;

    public IncomeRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        incomeDAO = db.getIncomeDAO();
        incomes = incomeDAO.findAllIncomes();

    }

    public LiveData<Income> getIncome(Long id) {
        this.income = incomeDAO.findIncomeById(id);
        return income;
    }

    public LiveData<List<Income>> getIncomesByDate(String date)
    {
        return incomeDAO.findIncomeByDate(date);
    }

    public LiveData<IncomeCategory> getCategory() {
        return category;
    }

    public LiveData<IncomeCategory> getCategory(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.category = incomeDAO.findIncomeCategory(id);
        });
        return category;
    }

    public LiveData<User> getUser() {
        return user;
    }
    public LiveData<User> getUser(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.user = incomeDAO.findIncomeUser(id);
        });
        return user;
    }

    public void insertIncome(Income income)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.addIncome(income);
        });
    }
    public void updateIncome(Income income)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.updateIncome(income);
        });
    }
    public void deleteIncome(Income income)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDAO.deleteIncome(income);
        });
    }

    public LiveData<List<Income>> getAllIncomes() {
        return incomes;
    }

    public LiveData<Long> totalByDate(String date)
    {
        return incomeDAO.totalByDate(date);
    }

    public LiveData<Long> totalByDateAndCate(String date,Long cateid)
    {
        return incomeDAO.totalByDateAndCate(date,cateid);
    }

    public LiveData<List<Income>> getIncomeByDateAlike(String date){
        return incomeDAO.getIncomeByDateAlike(date);
    }
}
