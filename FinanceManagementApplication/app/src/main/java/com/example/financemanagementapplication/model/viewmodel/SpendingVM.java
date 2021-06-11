package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.repository.SpendingRepository;

import java.util.List;

public class SpendingVM extends AndroidViewModel {
    private SpendingRepository repository ;
    private final LiveData<List<Spending>> spendings;
    private MutableLiveData<Long> id = new MutableLiveData<>();

    final public LiveData<Spending> tmp = Transformations.switchMap(this.id, input -> {
        return repository.getSpending(input);
    });

    public SpendingVM(Application application) {
        super(application);
        repository = new SpendingRepository(application);
        spendings = repository.getAllSpendings();
    }

    public LiveData<List<Spending>> getSpendings(){
        return spendings;
    }

    public void setId(Long id) { this.id.setValue(id);}

    public void insertSpending(Spending spending)
    {
        repository.insertSpending(spending);
    }

    public void updateSpending(Spending spending)
    {
        repository.updateSpending(spending);
    }

    public void deleteSpending(Spending spending)
    {
        repository.deleteSpending(spending);
    }

    public LiveData<List<Spending>> getSpendingByDate(String date)
    {
        return repository.getSpendingByDate(date);
    }

    public LiveData<Spending> getSpendingById(Long id)
    {
        return repository.getSpending(id);
    }

    public LiveData<Long> totalByDate(String date){
        return repository.totalByDate(date);
    }

    public LiveData<Long> totalByDateAndCate(String date,Long cateid){
        return repository.totalByDateAndCate(date,cateid);
    }
    public LiveData<List<Spending>> getSpendingByDateAlike(String date){
        return repository.getSpendingByDateAlike(date);
    }
}
