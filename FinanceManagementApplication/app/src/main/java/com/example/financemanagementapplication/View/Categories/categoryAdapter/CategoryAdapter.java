package com.example.financemanagementapplication.View.Categories.categoryAdapter;

import android.content.Context;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.parents.Category;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryAdapter<T extends Category> extends ArrayAdapter<T> {
    private Context context;
    private int resource;
    private boolean edit = false;
    private boolean switchLeft;
    private SpendingCategoryVM svm;
    private LifecycleOwner lco;
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<T> objects, Boolean editMode) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.edit = editMode;
    }
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<T> objects, Boolean editMode, SpendingCategoryVM svm, LifecycleOwner lco) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.edit = editMode;
        this.svm = svm;
        this.lco = lco;
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
        String name = getItem(position).getName();
        String desc = getItem(position).getDescription();

        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }else
            view = convertView;

        TextView nameTv = view.findViewById(R.id.category_name_ele);
        TextView limitTv = view.findViewById(R.id.limit_cate_ele);
        nameTv.setText(name);
        if (getItem(position) instanceof  SpendingCategory)
        {
            svm.getLimitationsForCategory(getItem(position).getId()).observe(lco,limitation -> {
                if (limitation != null)
                {
                    limitTv.setText(limitation.getPercentage());
                    if (limitation.isCritical()) limitTv.setTextColor(view.getResources().getColor(R.color.red));
                    else limitTv.setTextColor(view.getResources().getColor(R.color.green));
                }
                else limitTv.setText("Unlimited");
            });
        }
        FloatingActionButton btn = view.findViewById(R.id.button_category_ele);
        if (edit)
        {
            btn.setEnabled(true);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putLong("id", getItem(position).getId());
                    if (getItem(position) instanceof IncomeCategory)
                        b.putString("type","income_cate");
                    else b.putString("type","spending_cate");
                    Navigation.findNavController(v).navigate(R.id.action_categories_nav_to_updateCategoryForm,b);
                }
            });
        }
        else{
            btn.setEnabled(false);
        }

        return view;
    }
}
