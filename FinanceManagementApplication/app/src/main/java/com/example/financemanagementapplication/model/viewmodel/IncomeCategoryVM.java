package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.entity.CategoryAndIncomes;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.repository.IncCategoryRepository;

import java.util.List;

public class IncomeCategoryVM extends AndroidViewModel {
    private IncCategoryRepository repository ;
    private final LiveData<List<IncomeCategory>> categories;

    public IncomeCategoryVM(Application application) {
        super(application);
        repository = new IncCategoryRepository(application);
        categories = repository.getAllIncomeCategorys();
    }

    public LiveData<List<IncomeCategory>> getIncomeCategories(){
        return categories;
    }

    public void insertIncomeCategory(IncomeCategory spending)
    {
        repository.insertCategory(spending);
    }

    public void updateIncomeCategory(IncomeCategory spending)
    {
        repository.updateCategory(spending);
    }

    public void deleteIncomeCategory(IncomeCategory spending)
    {
        repository.deleteCategory(spending);
    }

    public LiveData<IncomeCategory> getIncomeCategoryById(Long id)
    {
        return repository.getCategory(id);
    }

    public LiveData<List<CategoryAndIncomes>> getIncomeForCate()
    {
        return repository.getIncomeForCate();
    }
}
