package com.example.financemanagementapplication.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.financemanagementapplication.model.database.dao.IncomeCategoryDAO;
import com.example.financemanagementapplication.model.database.dao.IncomeDAO;
import com.example.financemanagementapplication.model.database.dao.LimitationDAO;
import com.example.financemanagementapplication.model.database.dao.PeriodPaymentDAO;
import com.example.financemanagementapplication.model.database.dao.SpendingCategoryDAO;
import com.example.financemanagementapplication.model.database.dao.SpendingDAO;
import com.example.financemanagementapplication.model.database.dao.UserDAO;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Spending.class, Income.class,
        IncomeCategory.class, SpendingCategory.class, Limitation.class,
        PeriodPayment.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDAO getUserDAO();
    public abstract IncomeDAO getIncomeDAO();
    public abstract SpendingDAO getSpendingDAO();
    public abstract SpendingCategoryDAO getSpendingCategoryDAO();
    public abstract IncomeCategoryDAO getIncomeCategoryDAO();
    public abstract LimitationDAO getLimitationDAO();
    public abstract PeriodPaymentDAO getPeriodPaymentDAO();

    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyDatabase getDatabase(final Context context){
        if (INSTANCE == null)
        {
            synchronized (MyDatabase.class)
            {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "mydatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}
