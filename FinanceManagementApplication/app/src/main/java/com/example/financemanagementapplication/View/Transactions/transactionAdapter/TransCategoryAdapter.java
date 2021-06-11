package com.example.financemanagementapplication.View.Transactions.transactionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.dialog.SelectDialog;
import com.example.financemanagementapplication.model.database.entity.parents.Category;

import java.util.List;

public class TransCategoryAdapter<T extends Category> extends ArrayAdapter<T>
{
    private Context context;
    private int resource;
    private Long select_id;
    public TransCategoryAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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
        Long id = getItem(position).getId();
        TextView nameTv = view.findViewById(R.id.transaction_name);
        nameTv.setText(getItem(position).getName());
        return view;
    }
}
