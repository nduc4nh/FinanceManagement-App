package com.example.financemanagementapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DatetimeDialog extends DialogFragment {
    public DatetimeDialog() {
    }
    String chosenDate;
    public interface DatetimeDialogListener {
        public void chooseAllTime(DialogFragment dialog);
        public void chooseToday(DialogFragment dialog);
        public void chooseSelectDay(DialogFragment dialog);
        public void chooseThisMonth(DialogFragment dialog);
    }
    // Use this instance of the interface to deliver action events
    DatetimeDialog.DatetimeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the DatetimeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DatetimeDialogListener so we can send events to the host
            listener = (DatetimeDialog.DatetimeDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement DatetimeDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_datetime_menu,null);
        Button atbtn = view.findViewById(R.id.button_alltime);
        Button tdbtn = view.findViewById(R.id.bubtton_today);
        Button sdbtn = view.findViewById(R.id.button_selectday);
        Button tmbtn = view.findViewById(R.id.button_thismonth);

        atbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chooseAllTime(DatetimeDialog.this);
            }
        });
        tdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chooseToday(DatetimeDialog.this);
            }
        });
        sdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chooseSelectDay(DatetimeDialog.this);
            }
        });
        tmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chooseThisMonth(DatetimeDialog.this);
            }
        });
        builder.setTitle("Datetime Menu")
                .setView(view);
        return builder.create();
    }
}
