package com.example.financemanagementapplication.View.Transactions.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.transactionAdapter.TransCategoryAdapter;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;

import java.util.ArrayList;
import java.util.List;

public class SelectDialog extends DialogFragment {
    private int size;
    private ListView l;
    private TransCategoryAdapter adapter;
    private Boolean switchLeft;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private AlertDialog.Builder builder;
    private LifecycleOwner lco;
    private Long seclected;
    private List<IncomeCategory> ics;
    private List<SpendingCategory> scs;
    private  String[] a;
    public SelectDialog(Boolean sw, LifecycleOwner lco) {
        this.switchLeft = sw;
        this.lco = lco;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        ivm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        LiveData<List<IncomeCategory>> ic = ivm.getIncomeCategories();
        LiveData<List<SpendingCategory>> sc = svm.getSpendingCategories();
        if (switchLeft)
        {
            ic.observe(lco, incomeCategories -> {
                adapter = new TransCategoryAdapter(getContext(),R.layout.ele_list_finance_fragment, incomeCategories);
                ics = incomeCategories;
                a = new String[ics.size()];
                for (int i = 0; i < ics.size(); i++)
                {
                    a[i] = ics.get(i).getName();
                }
            });
        }
        else
        {
            sc.observe(lco, spendingCategories -> {
                adapter = new TransCategoryAdapter(getContext(),R.layout.ele_list_finance_fragment, spendingCategories);
                scs = spendingCategories;
                a = new String[scs.size()];
                for (int i = 0; i < scs.size(); i++)
                {
                    a[i] = scs.get(i).getName();
                }
            });
        }
        Log.d("g","gg");
        builder.setSingleChoiceItems(a,-1,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (switchLeft){
                    ic.observe(lco, incomeCategories -> {
                        seclected = incomeCategories.get(which).getId();
                    });
                }
                else
                {
                    sc.observe(lco, spendingCategories -> {
                        seclected = spendingCategories.get(which).getId();
                    });
                }
                dialog.dismiss();
            }
        }).setNegativeButton("Ok",null);
        //View view = getLayoutInflater().inflate(R.layout.fragment_transactions_list, null);
        return builder.create();
    }
    public Long getSeclected(){return this.seclected;}
}
