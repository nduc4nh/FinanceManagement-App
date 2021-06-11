package com.example.financemanagementapplication.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.SpendingCategoryDAO;
import com.example.financemanagementapplication.model.database.entity.CategoryAndLimitation;
import com.example.financemanagementapplication.model.database.entity.CategoryAndPeriods;
import com.example.financemanagementapplication.model.database.entity.CategoryAndSpendings;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

public class SpdCategoryRepository {
    private SpendingCategoryDAO spendingDao;
    private LiveData<List<SpendingCategory>> categories;
    private LiveData<List<CategoryAndSpendings>> spendings;
    private LiveData<SpendingCategory> category;
    private LiveData<CategoryAndLimitation> limitation;
    private LiveData<CategoryAndPeriods> periodpayment;

    public SpdCategoryRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        spendingDao = db.getSpendingCategoryDAO();
        categories = spendingDao.findAllSpendingCategory();
    }

    public LiveData<SpendingCategory> getCategory(Long id) {
        this.category = spendingDao.findSpendingCategoryById(id);
        Log.d("Repo", String.valueOf(this.category == null));
        return category;
    }

    public LiveData<List<CategoryAndSpendings>> getSpendingForCate(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.spendings = spendingDao.getSpendingCategorySpendings();
        });
        return spendings;
    }

    public LiveData<CategoryAndLimitation> getLimitation() {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.limitation = spendingDao.getSpendingCategoryLimitation();
        });
        return limitation;
    }

    public LiveData<CategoryAndPeriods> getPeriodpayment() {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.spendings = spendingDao.getSpendingCategoryPeriods();
        });
        return periodpayment;
    }

    public void insertCategory(SpendingCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDao.addSpendingCategory(category);
        });
    }
    public void updateCategory(SpendingCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDao.updateSpendingCategory(category);
        });
    }
    public void deleteCategory(SpendingCategory category)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            spendingDao.deleteSpendingCategory(category);
        });
    }

    public LiveData<List<SpendingCategory>> getAllSpendingCategorys() {
        return categories;
    }

    public LiveData<Limitation> getLimitationsForCategory(Long cateId)
    {
        return spendingDao.getLimitationsForCategory(cateId);
    }
}
