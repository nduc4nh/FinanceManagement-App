package com.example.financemanagementapplication.View.Transactions.transactionAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.PeriodPaymentVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.DateTool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class PeriodAdapter extends ArrayAdapter<PeriodPayment> {
    private Context context;
    private int resource;
    private int count;
    private PeriodPaymentVM pvm;
    public PeriodAdapter(@NonNull Context context, int resource, @NonNull List<PeriodPayment> objects, PeriodPaymentVM pvm){
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.pvm = pvm;
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
        CardView eleCard = view.findViewById(R.id.ele_card);
        PeriodPayment item = getItem(position);
        nameTv.setText(item.getName());
        dateTv.setText(item.getState(DateTool.getMonth(DateTool.today())));
        cateTv.setText(Converter.rawToReadable(item.getAmount()));
        amountTv.setText("Double tap here to delete");
        amountTv.setTextColor(view.getResources().getColor(R.color.red));
        amountTv.setClickable(true);
        amountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putLong("id", item.getId());
                LocalDateTime x = LocalDateTime.now();
                int tmp = x.getSecond();
                if (tmp - count == 0)
                {
                    Log.d("sec","double clicked");
                    pvm.deletePeriodPayment(item);
                }
                count = tmp;
                //Navigation.findNavController(v).navigate(R.id.action_transaction_nav_to_periodUpdate,b);
            }
        });

        return view;
    }

}

