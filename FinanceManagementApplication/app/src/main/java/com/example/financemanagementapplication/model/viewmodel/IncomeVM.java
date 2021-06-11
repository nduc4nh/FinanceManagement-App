package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;
import android.app.Presentation;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.repository.IncomeRepository;

import java.util.List;

public class IncomeVM extends AndroidViewModel {
    private IncomeRepository repository ;
    private final LiveData<List<Income>> incomes;
    private MutableLiveData<Long> id = new MutableLiveData<>();
    public final LiveData<Income> tmp = Transformations.switchMap(this.id, input -> {
        return repository.getIncome(input);
    });
    public IncomeVM(Application application) {
        super(application);
        repository = new IncomeRepository(application);
        incomes = repository.getAllIncomes();
    }

    public LiveData<List<Income>> getIncomes(){
        return incomes;
    }

    public void setId(Long id){ this.id.setValue(id);}

    public void insertIncome(Income income)
    {
        repository.insertIncome(income);
    }

    public void updateIncome(Income income)
    {
        repository.updateIncome(income);
    }

    public void deleteIncome(Income income)
    {
        repository.deleteIncome(income);
    }

    public LiveData<List<Income>> getIncomesByDate(String date)
    {
        return repository.getIncomesByDate(date);
    }

    public LiveData<Income> getIncomeById(Long id)
    {
        return repository.getIncome(id);
    }

    public LiveData<Long> totalByDate(String date){
        return repository.totalByDate(date);
    }

    public LiveData<Long> totalByDateAndCate(String date,Long cateid){
        return repository.totalByDateAndCate(date,cateid);
    }
    public LiveData<List<Income>> getIncomeByDateAlike(String date){
        return repository.getIncomeByDateAlike(date);
    }
}
