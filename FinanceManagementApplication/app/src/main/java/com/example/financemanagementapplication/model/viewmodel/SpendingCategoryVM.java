package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.repository.SpdCategoryRepository;

import java.util.List;

public class SpendingCategoryVM extends AndroidViewModel {
    private SpdCategoryRepository repository ;
    private final LiveData<List<SpendingCategory>> categories;

    public SpendingCategoryVM(Application application) {
        super(application);
        repository = new SpdCategoryRepository(application);
        categories = repository.getAllSpendingCategorys();
    }

    public LiveData<List<SpendingCategory>> getSpendingCategories(){
        return categories;
    }

    public void insertSpendingCategory(SpendingCategory spending)
    {
        repository.insertCategory(spending);
    }

    public void updateSpendingCategory(SpendingCategory spending)
    {
        repository.updateCategory(spending);
    }

    public void deleteSpendingCategory(SpendingCategory spending)
    {
        repository.deleteCategory(spending);
    }

    public LiveData<SpendingCategory> getSpendingCategoryById(Long id)
    {
        return repository.getCategory(id);
    }
    public LiveData<Limitation> getLimitationsForCategory(Long cateId)
    {
        return repository.getLimitationsForCategory(cateId);
    }
}
