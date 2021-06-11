package com.example.financemanagementapplication.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.financemanagementapplication.model.database.MyDatabase;
import com.example.financemanagementapplication.model.database.dao.UserDAO;
import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.database.entity.UserAndIncomes;
import com.example.financemanagementapplication.model.database.entity.UserAndSpendings;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;
    private LiveData<User> user;
    private LiveData<List<UserAndSpendings>> userSpending;
    private LiveData<List<UserAndIncomes>> userIncome;

    public UserRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        userDAO = db.getUserDAO();
        allUsers = userDAO.findAllUser();
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
    public LiveData<User> getUser() { return user;}

    public LiveData<User> getUserById(Long id) {
        this.user = userDAO.findUserById(id);
        Log.d("Repo user", String.valueOf(this.user == null));
        return this.user;
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<UserAndSpendings>> getUserSpending() {
        return userSpending;
    }

    public LiveData<List<UserAndIncomes>> getUserIncome() {
        return userIncome;
    }

    public void insertUser(User newUser)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.addUser(newUser);
        });
    }
    public void updateUser(User newUser)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUser(newUser);
        });
    }
    public void deleteUser(User newUser)
    {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteUser(newUser);
        });
    }
}
