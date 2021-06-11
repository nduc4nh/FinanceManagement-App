package com.example.financemanagementapplication.View.Categories;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.Converter;

public class createCategoryForm extends Fragment {
    public createCategoryForm() {
    }
    private boolean switchLeft;
    private  CategoryInput cur;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private LimitationVM lvm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar tb = getActivity().findViewById(R.id.toolbar);
        switchLeft = getArguments().getBoolean("switchLeft");
        if (switchLeft) tb.setTitle("Create Category - Income");
        else tb.setTitle("Create Category - Spending");
        ivm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        lvm = new ViewModelProvider(requireActivity()).get(LimitationVM.class);
    }
    //creating limitation with the same id as category so that it'll be easier to query
    //. However, It's hard to implement when the initial phase needs a hash-map to read all the limitation
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form,container,false);
        Button backBtn = view.findViewById(R.id.button_back);
        Button updateBtn = view.findViewById(R.id.button_save);
        cur = new CategoryInput(switchLeft);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.form_container,cur).commit();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft",switchLeft);
                Navigation.findNavController(v).navigate(R.id.action_createCategoryForm_to_categoriesFragment,b);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            Boolean confirm = true;
            @Override
            public void onClick(View v) {
                String name = cur.getName();
                String desc = cur.getDesc();
                String limitReadable = cur.getLimit();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                if (!name.equals("")) {
                    builder2.setTitle("Confirmation")
                            .setMessage(String.format("Are you sure to create a category with name: '%s'?",name))
                            .setNegativeButton("No", null).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (switchLeft) {
                                IncomeCategory ic = new IncomeCategory(name, desc);
                                ivm.insertIncomeCategory(ic);
                            } else {
                                SpendingCategory sc = new SpendingCategory(name, desc);
                                if (!limitReadable.equals("")) {
                                    Limitation limitation = new Limitation(sc.getId(), name, 0l, Converter.readableToRaw(limitReadable), sc.getId());
                                    lvm.insertLimitation(limitation);
                                }
                                svm.insertSpendingCategory(sc);
                            }
                            Bundle b = new Bundle();
                            b.putBoolean("switchLeft", switchLeft);
                            Navigation.findNavController(v).navigate(R.id.action_createCategoryForm_to_categoriesFragment, b);
                        }
                    });
                }
                else {
                    builder2.setTitle("Alert")
                            .setMessage("You tend to create a category having no name. Denied creating!")
                            .setPositiveButton("Ok", null);
                }
                builder2.create().show();
            }
        });
        return view;
    }
}
