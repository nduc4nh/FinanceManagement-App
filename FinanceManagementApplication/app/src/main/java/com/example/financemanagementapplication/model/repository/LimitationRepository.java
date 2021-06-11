package com.example.financemanagementapplication.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.LimitationDAO;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

public class LimitationRepository {
    private LimitationDAO limitationDAO;
    private LiveData<List<Limitation>> limitations;
    private LiveData<Limitation> limitation;
    private LiveData<SpendingCategory> category;
    private Limitation tmp;

    public LimitationRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        limitationDAO = db.getLimitationDAO();
        limitations = limitationDAO.findAllLimitation();
    }

    public LiveData<Limitation> getLimitation(Long id) {
        this.limitation = limitationDAO.findLimitationById(id);
        return limitation;
    }

    public LiveData<SpendingCategory> getCategory(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.category = limitationDAO.getLimitationCategory(id);
        });
        return category;
    }

    public void insertLimitation(Limitation limitation)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            limitationDAO.addLimitation(limitation);
        });
    }

    public void updateLimitation(Limitation limitation)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            limitationDAO.updateLimitation(limitation);
        });
    }

    public void deleteLimitation(Limitation limitation)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            limitationDAO.deleteLimitation(limitation);
        });
    }

    public LiveData<List<Limitation>> getAllLimitations() {
        return limitations;
    }
    public LiveData<Limitation> getLimitationWithCategory(Long cateId){
        return limitationDAO.getLimitationWithCategory(cateId);
    }

    public Limitation getLimitationWithCategoryRaw(Long cateId){
        MyDatabase.databaseWriteExecutor.execute(() -> {
            tmp = limitationDAO.getLimitationWithCategoryRaw(cateId);
        });
        return tmp;
    }
}
