package com.example.financemanagementapplication.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.repository.UserRepository;

import java.util.List;

public class UserVM extends AndroidViewModel{
    private UserRepository repository ;
    private final LiveData<List<User>> users;
    private final MutableLiveData<Long> id = new MutableLiveData();
    public  LiveData<User> tmp = Transformations.switchMap(this.id,input -> {
            return repository.getUserById(input);
    });
    public UserVM(Application application) {
        super(application);
        repository = new UserRepository(application);
        users = repository.getAllUsers();
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    public LiveData<User> getUserById(Long id){return repository.getUserById(id);}


    public void insertUser(User user)
    {
        repository.insertUser(user);
    }

    public void updateUser(User user)
    {
        repository.updateUser(user);
    }

    public void deleteUser(User user)
    {
        repository.deleteUser(user);
    }

    public void setId(Long id)
    {
        this.id.setValue(id);
    }
}
