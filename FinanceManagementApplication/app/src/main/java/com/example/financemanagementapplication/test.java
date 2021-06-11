package com.example.financemanagementapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financemanagementapplication.View.GeneralDialog;
import com.example.financemanagementapplication.View.Transactions.TransactionInput;
import com.example.financemanagementapplication.View.Transactions.dialog.CalendarDialog;
import com.example.financemanagementapplication.tool.DateTool;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class test extends Fragment implements Test_Dialog.Test_DialogListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private FloatingActionButton btn;
    Wrapper wrapper;
    private TextView label;
    public test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment test.
     */
    // TODO: Rename and change types and number of parameters
    public static test newInstance(String param1, String param2) {
        test fragment = new test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (getArguments() != null)
        {
            wrapper = (Wrapper) getArguments().getSerializable("dialog");
        }
    }

    public View onCreateView1(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_test, container, false);;

        CalendarView cv = view.findViewById(R.id.calendarView);
        CheckBox check = view.findViewById(R.id.checkbox_period);
        btn = view.findViewById(R.id.test_button_float);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    /*Test_Dialog cd = new Test_Dialog();
                    cd.setTargetFragment(test.this,0);
                    cd.show(getParentFragmentManager(),null);*/
                }
            }
        });
        //cv.get

        if (wrapper != null)
        {
            wrapper.getDialog().show(getParentFragmentManager(),null);
        }
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });

        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.support_fragment, container, false);


        return view;
    }

    @Override
    public void chooseIcon(DialogFragment dialog, int checkedId) {
        if (checkedId == R.id.entertainment)
        {
            btn.setImageResource(R.drawable.ic_entertainment);
        }
        if (checkedId == R.id.grocery)
        {
            btn.setImageResource(R.drawable.ic_grocery);
        }
        if (checkedId == R.id.relationship)
        {
            btn.setImageResource(R.drawable.ic_relationship);
        }
        if (checkedId == R.id.work)
        {
            btn.setImageResource(R.drawable.ic_work);
        }
    }
}