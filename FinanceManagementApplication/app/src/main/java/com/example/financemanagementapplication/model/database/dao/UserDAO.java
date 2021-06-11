package com.example.financemanagementapplication.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.database.entity.UserAndIncomes;
import com.example.financemanagementapplication.model.database.entity.UserAndSpendings;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("select * from users")
    public LiveData<List<User>> findAllUser();
    @Query("select * from users " +
            "where users.id = :user_id")
    public LiveData<User> findUserById(Long user_id);
    @Query("select * from users " +
            "where users.name like :name")
    public LiveData<User> findUserByName(String name);

    @Transaction
    @Query("select * from users")
    public LiveData<List<UserAndSpendings>> getUserSpendings();

    @Transaction
    @Query("select * from users")
    public LiveData<List<UserAndIncomes>> getUserIncomes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addUser(User user);
    @Update
    public void updateUser(User user);
    @Delete
    public void deleteUsers(List<User> users);
    @Delete
    public void deleteUser(User user);

}
