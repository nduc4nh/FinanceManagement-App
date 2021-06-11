package com.example.financemanagementapplication.View.Categories;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.SupportToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean switchLeft = false;
    private boolean editMode = false;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private Toolbar tb;
    private SpendingCategoriesList spendingCategoriesList ;
    private IncomeCategoriesList incomeCategoriesList;
    private View view;


    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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
        tb = getActivity().findViewById(R.id.toolbar);
        tb.setTitle("Categories");

        Bundle bundle = getArguments();
        if (bundle != null) switchLeft = bundle.getBoolean("switchLeft");
        if (bundle != null) {
            editMode = bundle.getBoolean("editMode");
            Log.d("Edit", String.valueOf(editMode));
        }
        if (switchLeft) tb.setTitle("Categories - Income");
        else tb.setTitle("Categories - Spending");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);


        FloatingActionButton buttonCreate = view.findViewById(R.id.button_create_categories);
        FloatingActionButton buttonEdit = view.findViewById(R.id.button_edit_categories);
        Switch switchItem = view.findViewById(R.id.switch_item);

        switchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    incomeCategoriesList = new IncomeCategoriesList(editMode);
                    tb.setTitle("Categories - Income");
                   // switchItem.setText("to Spending");
                    //Toast
                    Toast t = Toast.makeText(getContext(),"Income categories",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER, 0, 300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(1000);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.categories_container, incomeCategoriesList).commit();
                }
                else {
                    tb.setTitle("Categories - Spending");
                    //switchItem.setText("to Income");
                    spendingCategoriesList = new SpendingCategoriesList(editMode);

                    Toast t = Toast.makeText(getContext(),"Spending categories",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER, 0, 300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(1000);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.categories_container, spendingCategoriesList).commit();
                }
                buttonCreate.setEnabled(!editMode);
                switchLeft = isChecked;
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.categories_container,curr).commit();
            }
        });
        switchItem.setChecked(switchLeft);

        if (switchLeft)
        {
            //curr = new IncomeCategoriesList(editMode);
            //switchItem.setText("to Income");
            incomeCategoriesList = new IncomeCategoriesList(editMode);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.categories_container,incomeCategoriesList).commit();
        }
        else
        {
            //curr = new SpendingCategoriesList(editMode);
            //switchItem.setText("to Spending");
            spendingCategoriesList = new SpendingCategoriesList(editMode);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.categories_container,spendingCategoriesList).commit();
        }

        buttonCreate.setEnabled(!editMode);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Log.d("tryhard",String.valueOf(switchLeft));
                b.putBoolean("switchLeft", switchLeft);
                Navigation.findNavController(v).navigate(R.id.action_categoriesFragment_to_createCategoryForm,b);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Log.d("tryhard",String.valueOf(switchLeft));
                b.putBoolean("switchLeft", switchLeft);
                editMode = !editMode;
                setListState(editMode);
                buttonCreate.setEnabled(!editMode);
                Toast t = Toast.makeText(getContext(),"Edit "+(editMode?"Enabled":"Disabled"),Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                SupportToast sp = new SupportToast(t);
                sp.showToast(500);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void setListState(boolean editMode)
    {
        if (switchLeft)
        {
            incomeCategoriesList.update(editMode);
        }
        else
        {
            spendingCategoriesList.update(editMode);
        }
    }
}