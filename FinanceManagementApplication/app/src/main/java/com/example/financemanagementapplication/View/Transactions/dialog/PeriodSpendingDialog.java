package com.example.financemanagementapplication.View.Transactions.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ListAdapter;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.transactionAdapter.PeriodAdapter;
import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.viewmodel.PeriodPaymentVM;

import java.util.List;

public class PeriodSpendingDialog extends DialogFragment {

    public interface PeriodSpendingDialogListener {
    }
    // Use this instance of the interface to deliver action events
    PeriodSpendingDialog.PeriodSpendingDialogListener listener;
    private ListView lv;
    private List<PeriodPayment> periodList;
    private PeriodAdapter periodAdapter;
    private Long[] referId;
    private  PeriodPaymentVM pvm;
    private LifecycleOwner lco;
    private Context context;
    public PeriodSpendingDialog(Context context,LifecycleOwner lco) {
        this.lco = lco;
        this.context = context;
    }
    // Override the Fragment.onAttach() method to instantiate the CalendarDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CalendarDialogListener so we can send events to the host
            listener = ( PeriodSpendingDialog.PeriodSpendingDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement CalendarDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pvm = new ViewModelProvider(requireActivity()).get(PeriodPaymentVM.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_transactions_list,null);
        lv = view.findViewById(R.id.trans_list_ele);
        pvm.getPeriodPayments().observe(lco,periodPayments -> {
            periodAdapter = new PeriodAdapter(context,R.layout.ele_list_finance_fragment,periodPayments,pvm);
            lv.setAdapter(periodAdapter);
            Log.d("abc",periodPayments.toString());
        });
        builder.setView(view).setTitle("Monthly Payment").setNegativeButton("Ok",null);
        return builder.create();
    }
}
