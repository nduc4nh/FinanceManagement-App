package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.repository.LimitationRepository;

import java.util.List;

public class LimitationVM extends AndroidViewModel {
    private LimitationRepository repository ;
    private final LiveData<List<Limitation>> limitations;
    private LiveData<Limitation> limitation;

    public LimitationVM(Application application) {
        super(application);
        repository = new LimitationRepository(application);
        limitations = repository.getAllLimitations();
    }

    public LiveData<List<Limitation>> getLimitations(){
        return limitations;
    }

    public void insertLimitation(Limitation limitation)
    {
        repository.insertLimitation(limitation);
    }

    public void updateLimitation(Limitation limitation)
    {
        repository.updateLimitation(limitation);
    }

    public void deleteLimitation(Limitation limitation)
    {
        repository.deleteLimitation(limitation);
    }
    public LiveData<Limitation> getLimitationWithCategory(Long cateId){
        limitation = repository.getLimitationWithCategory(cateId);
        return  limitation;
    }
    public Limitation getLimitationWithCategoryRaw(Long cateId){
        return repository.getLimitationWithCategory(cateId).getValue();
    }
    public LiveData<Limitation> getLimitationById(Long id)
    {
        limitation = repository.getLimitation(id);
        return limitation;
    }
}
