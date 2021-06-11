package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.repository.PeriodPaymentRepository;

import java.util.List;

public class PeriodPaymentVM extends AndroidViewModel {
    private PeriodPaymentRepository repository ;
    private final LiveData<List<PeriodPayment>> periodPayment;

    public PeriodPaymentVM(Application application) {
        super(application);
        repository = new PeriodPaymentRepository(application);
        periodPayment = repository.getAllPeriodPayments();
    }

    public LiveData<List<PeriodPayment>> getPeriodPayments(){
        return periodPayment;
    }

    public void insertPeriodPayment(PeriodPayment paypent)
    {
        repository.insertPeriodPayment(paypent);
    }

    public void updatePeriodPayment(PeriodPayment paypent)
    {
        repository.updatePeriodPayment(paypent);
    }

    public void deletePeriodPayment(PeriodPayment paypent)
    {
        repository.deletePeriodPayment(paypent);
    }
}
