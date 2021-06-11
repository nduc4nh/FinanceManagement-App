package com.example.financemanagementapplication.View.Transactions.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.financemanagementapplication.R;

public class CalendarDialog extends DialogFragment {
    public CalendarDialog() {
    }

    String chosenDate;
    boolean isIncome = false;
    public CalendarDialog(Boolean isIncome) {
        this.isIncome = isIncome;
    }

    public interface CalendarDialogListener {
        public void onCalendarClick(DialogFragment dialog,int year,int month,int day);
        public void onCheckPeriod(DialogFragment dialog, boolean checked);
    }
    // Use this instance of the interface to deliver action events
    CalendarDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the CalendarDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CalendarDialogListener so we can send events to the host
            listener = (CalendarDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement CalendarDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_test,null);
        CalendarView cv = view.findViewById(R.id.calendarView);
        CheckBox cb = view.findViewById(R.id.checkbox_period);
        if (isIncome) cb.setVisibility(View.GONE);
        cv.setSelected(false);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                listener.onCalendarClick(CalendarDialog.this, year,month+1,dayOfMonth);
            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onCheckPeriod(CalendarDialog.this, isChecked);
            }
        });
        builder.setTitle("Choose date")
                .setView(view)
                .setNegativeButton("Ok",null);
        return builder.create();
    }

    public String getChosenDate()
    {
        return this.chosenDate;
    }
}
