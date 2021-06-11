package com.example.financemanagementapplication.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.PeriodPaymentDAO;
import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;

import java.util.List;

public class PeriodPaymentRepository {
    private PeriodPaymentDAO periodPayementDAO;
    private LiveData<List<PeriodPayment>> periodPayements;
    private LiveData<PeriodPayment> periodPayement;
    private LiveData<SpendingCategory> category;

    public PeriodPaymentRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        periodPayementDAO = db.getPeriodPaymentDAO();
        periodPayements = periodPayementDAO.findAllPeriodPayment();
    }

    public LiveData<PeriodPayment> getPeriodPayment(Long id) {
        this.periodPayement = periodPayementDAO.findPeriodPaymentById(id);
        return periodPayement;
    }

    public LiveData<SpendingCategory> getCategory(Long id) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            this.category = periodPayementDAO.getPeriodPaymentSpendingCategory(id);
        });
        return category;
    }

    public void insertPeriodPayment(PeriodPayment periodPayement)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            periodPayementDAO.addPeriodPayment(periodPayement);
        });
    }
    public void updatePeriodPayment(PeriodPayment periodPayement)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            periodPayementDAO.updatePeriodPayment(periodPayement);
        });
    }
    public void deletePeriodPayment(PeriodPayment periodPayement)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            periodPayementDAO.deletePeriodPayment(periodPayement);
        });
    }

    public LiveData<List<PeriodPayment>> getAllPeriodPayments() {
        return periodPayements;
    }
}
