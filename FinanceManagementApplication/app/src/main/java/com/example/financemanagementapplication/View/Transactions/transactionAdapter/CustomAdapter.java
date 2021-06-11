package com.example.financemanagementapplication.View.Transactions.transactionAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.parents.Category;
import com.example.financemanagementapplication.model.database.entity.parents.Finance;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.Converter;

import java.util.List;

public class CustomAdapter<T extends Finance> extends ArrayAdapter<T> {
    private Context context;
    private int resource;
    private boolean edit = false;
    private boolean switchLeft;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private LifecycleOwner lco;
    private Boolean alltime = false;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<T> objects,
                          Boolean editMode,IncomeCategoryVM ivm,SpendingCategoryVM svm, LifecycleOwner lco, boolean sl){
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.edit = editMode;
        this.ivm = ivm;
        this.svm = svm;
        this.lco = lco;
        this.switchLeft = sl;
    }
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<T> objects,
                         Boolean editMode,IncomeCategoryVM ivm,SpendingCategoryVM svm,
                         LifecycleOwner lco, boolean sl,Boolean alltime){
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.edit = editMode;
        this.ivm = ivm;
        this.svm = svm;
        this.lco = lco;
        this.switchLeft = sl;
        this.alltime = alltime;
    }
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    public void setSwitchLeft(boolean sl) {
        this.switchLeft = sl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }else
            view = convertView;
        TextView nameTv = view.findViewById(R.id.transaction_name);
        TextView amountTv = view.findViewById(R.id.transaction_amount);
        TextView cateTv = view.findViewById(R.id.transaction_category_name);
        TextView dateTv = view.findViewById(R.id.transaction_date_name);
        dateTv.setText(getItem(position).getDate());
        if (alltime)
        {
            dateTv.setVisibility(View.VISIBLE);
        }
        if (edit)
        {
            CardView card = view.findViewById(R.id.ele_card);
            card.setClickable(true);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putLong("id", getItem(position).getId());
                    if (getItem(position) instanceof Income)
                        b.putString("type","income");
                    else b.putString("type","spending");
                    Log.d("LOG", getItem(position).toString());
                    b.putString("cate_name",cateTv.getText()==null?"Conservation":cateTv.getText().toString());
                    b.putBoolean("alltime",alltime);
                    Log.d("alltime",""+alltime);
                    Navigation.findNavController(v).navigate(R.id.action_transaction_nav_to_updateTransactionForm,b);
                }
            });
        }

        String name = getItem(position).getName();
        Long category_id = getItem(position).getCategoryId();
        LiveData<IncomeCategory> ic;
        LiveData<SpendingCategory> sc;


        Long amount = getItem(position).getAmount();



        nameTv.setText(name);
        String amountReadable = Converter.rawToReadable(amount);

        if (switchLeft)
        {
            amountReadable = "+"+amountReadable;
            amountTv.setTextColor(view.getResources().getColor(R.color.green));
            ic = ivm.getIncomeCategoryById(category_id);
            ic.observe(lco, incomeCategory -> {
                if (incomeCategory != null) cateTv.setText(incomeCategory.getName());
            });
            //ic.removeObservers(lco);
        }
        else {
            Log.d("type","spending");
            amountReadable = "-"+amountReadable;
            amountTv.setTextColor(view.getResources().getColor(R.color.red));
            Log.d("Test id",String.valueOf(category_id) );
            Log.d("Test id",getItem(position).toString() );
            sc = svm.getSpendingCategoryById(category_id);
            sc.observe(lco, spendingCategory -> {
                if (cateTv != null && spendingCategory != null)
                    cateTv.setText(spendingCategory.getName());
            });
            //sc.removeObservers(lco);
        }
        amountTv.setText(amountReadable);
        return view;
    }

}
