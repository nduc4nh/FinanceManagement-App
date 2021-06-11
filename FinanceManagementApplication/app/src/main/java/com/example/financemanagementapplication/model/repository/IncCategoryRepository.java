package com.example.financemanagementapplication.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.IncomeCategoryDAO;
import com.example.financemanagementapplication.model.database.entity.CategoryAndIncomes;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;

import java.util.List;

public class IncCategoryRepository {
    private IncomeCategoryDAO incomeDao;
    private LiveData<List<IncomeCategory>> categories;
    private LiveData<List<CategoryAndIncomes>> incomes;
    private LiveData<IncomeCategory> category;

    public IncCategoryRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        incomeDao = db.getIncomeCategoryDAO();
        categories = incomeDao.findAllIncomeCategory();
    }

    public LiveData<IncomeCategory> getCategory(Long id) {
        this.category = incomeDao.findIncomeCategoryById(id);
        Log.d("Repo", String.valueOf(this.category == null));
        return this.category;
    }

    public LiveData<List<CategoryAndIncomes>> getIncomeForCate() {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.incomes = incomeDao.getIncomeCategoryIncomes();
        });
        return incomes;
    }

    public void insertCategory(IncomeCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDao.addIncomeCategory(category);
        });
    }
    public void updateCategory(IncomeCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDao.updateIncomeCategory(category);
        });
    }
    public void deleteCategory(IncomeCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            incomeDao.deleteIncomeCategory(category);
        });
    }

    public LiveData<List<IncomeCategory>> getAllIncomeCategorys() {
        return categories;
    }
}
