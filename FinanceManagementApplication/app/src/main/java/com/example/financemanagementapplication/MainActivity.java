package com.example.financemanagementapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.example.financemanagementapplication.View.Categories.CategoriesFragment;
import com.example.financemanagementapplication.View.Statistics.StatisticsFragment;
import com.example.financemanagementapplication.View.Transactions.TransactionsFragment;
import com.example.financemanagementapplication.View.User.UserFragment;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.PeriodPaymentVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.UserVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private PeriodPaymentVM pvm;
    private UserVM uvm;
    private LimitationVM lvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivm = new ViewModelProvider(this).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(this).get(SpendingCategoryVM.class);
        uvm = new ViewModelProvider(this).get(UserVM.class);
        pvm = new ViewModelProvider(this).get(PeriodPaymentVM.class);
        lvm = new ViewModelProvider(this).get(LimitationVM.class);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        Log.d("msg","test");
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.user_nav, R.id.categories_nav, R.id.transaction_nav, R.id.statistic_nav)
                .build();
        Toolbar tb = findViewById(R.id.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bnv, navController);
        getSupportActionBar().hide();
    }
}