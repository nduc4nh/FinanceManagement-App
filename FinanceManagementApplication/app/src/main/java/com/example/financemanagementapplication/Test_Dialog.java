package com.example.financemanagementapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class Test_Dialog extends DialogFragment {
    public Test_Dialog() {
    }
    String chosenDate;
    public interface Test_DialogListener {
        public void chooseIcon(DialogFragment dialog, int checkedId);
    }
    // Use this instance of the interface to deliver action events
    Test_Dialog.Test_DialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the Test_DialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the Test_DialogListener so we can send events to the host
            listener = (Test_Dialog.Test_DialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement Test_DialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.icon_list,null);
        RadioGroup cv = view.findViewById(R.id.icon_list);
        cv.clearCheck();
        cv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.chooseIcon(Test_Dialog.this, checkedId);
            }
        });
        builder.setTitle("Choose icon")
                .setView(view)
                .setNegativeButton("Ok",null);
        return builder.create();
    }
}
