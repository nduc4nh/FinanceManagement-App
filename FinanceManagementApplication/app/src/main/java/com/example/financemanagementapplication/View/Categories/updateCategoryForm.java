package com.example.financemanagementapplication.View.Categories;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.Converter;

public class updateCategoryForm extends Fragment {
    private IncomeCategoryVM vm1;
    private SpendingCategoryVM vm2;
    private LimitationVM lvm;
    public Long id;
    private LiveData<IncomeCategory> ic;
    private LiveData<SpendingCategory> sc;
    private LiveData<Limitation> l;
    public   String name,desc, limit;
    public boolean hasLimit = false;
    public updateCategoryForm() {
    }
    private CategoryInput tmp;
    private boolean switchLeft;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar tb = getActivity().findViewById(R.id.toolbar);
        tb.setTitle("Edit Category - "+(switchLeft?"Income":"Spending"));
        switchLeft = getArguments().getString("type").equals("income_cate");
        id = getArguments().getLong("id");
        vm1 =  new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        vm2 = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        lvm = new ViewModelProvider(requireActivity()).get(LimitationVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form,container,false);
        Button backBtn = view.findViewById(R.id.button_back);
        Button deleteBtn = view.findViewById(R.id.button_delete);
        Button updateBtn = view.findViewById(R.id.button_save);
        deleteBtn.setVisibility(View.VISIBLE);

        tmp = new CategoryInput(switchLeft);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.form_container,tmp);
        transaction.commit();
        //TableRow limitRow = cur.getView().findViewById(R.id.limit_row_cate);

        if (switchLeft) {
            ic = vm1.getIncomeCategoryById(id);
            ic.observe(getViewLifecycleOwner(),incomeCategory -> {
                if (tmp.getView()!=null) {
                    tmp.setName(incomeCategory.getName());
                    tmp.setDesc(incomeCategory.getDescription());
                }
                else {
                        Bundle b = new Bundle();
                        b.putString("type",getArguments().getString("type"));
                        b.putLong("id", id);
                        Navigation.findNavController(view).navigate(R.id.action_updateCategoryForm_self,b);
                }
            });
        }
        else {
            sc = vm2.getSpendingCategoryById(id);
            sc.observe(getViewLifecycleOwner(),spendingCategory -> {
                if (tmp.getView()!=null){
                    tmp.setName(spendingCategory.getName());
                    tmp.setDesc(spendingCategory.getDescription());}
                else{
                        Bundle b = new Bundle();
                        b.putString("type",getArguments().getString("type"));
                        b.putLong("id", id);
                        Navigation.findNavController(view).navigate(R.id.action_updateCategoryForm_self,b);
                }
            });
            l = lvm.getLimitationWithCategory(id);
            l.observe(getViewLifecycleOwner(),limitation -> {
                if (limitation != null)
                {
                    if(tmp.getView() != null) {
                        tmp.setLimit(Converter.rawToReadable(limitation.getMax()));
                        hasLimit = true;
                    }
                }
            });
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft", switchLeft);
                b.putBoolean("editMode", true);
                Navigation.findNavController(v).navigate(R.id.action_updateCategoryForm_to_categories_nav,b);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                if (!tmp.getName().equals(""))
                {
                    builder2.setTitle("Confirmation").setMessage("Are you sure to make change of this category? \n\n" +
                            " Name: " + tmp.getName() +"\n" +
                            " Limit: " + (tmp.getLimit().equals("")?"Unlimited Spending":"max "+tmp.getLimit()))
                            .setNegativeButton("No",null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (switchLeft) {
                                ic.observe(getViewLifecycleOwner(),incomeCategory -> {
                                    incomeCategory.setName(tmp.getName());
                                    incomeCategory.setDescription(tmp.getDesc());
                                    vm1.updateIncomeCategory(incomeCategory);
                                });
                            } else {
                                sc.observe(getViewLifecycleOwner(), spendingCategory -> {
                                    spendingCategory.setName(tmp.getName());
                                    spendingCategory.setDescription(tmp.getDesc());
                                    vm2.updateSpendingCategory(spendingCategory);
                                });
                                l.observe(getViewLifecycleOwner(), limitation -> {
                                    if (tmp.getLimit().equals(""))
                                    {
                                        if (hasLimit) lvm.deleteLimitation(limitation);
                                    }
                                    else {
                                        if (hasLimit)
                                        {limitation.setMax(Converter.readableToRaw(tmp.getLimit()));
                                            lvm.updateLimitation(limitation);}
                                        else {
                                            lvm.insertLimitation(new Limitation(id,"name",0l,Converter.readableToRaw(tmp.getLimit()),id));
                                        }
                                    }
                                });
                            }
                            Bundle b = new Bundle();
                            b.putBoolean("switchLeft", switchLeft);
                            b.putBoolean("editMode", true);
                            Navigation.findNavController(v).navigate(R.id.action_updateCategoryForm_to_categories_nav,b);
                        }
                    });
                }
                else {
                    builder2.setTitle("Alert")
                            .setMessage("You tend to update a category having no name. Denied updating!")
                            .setPositiveButton("Ok", null);
                }
                builder2.create().show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                builder2.setTitle("Delete")
                        .setMessage("Are you sure to delete this category? \n\n" +
                                " Name: " + tmp.getName() +"\n" +
                                " Limit " + (tmp.getLimit().equals("")?"Unlimited spending":"max "+tmp.getLimit()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (switchLeft) {
                                    ic.observe(getViewLifecycleOwner(), incomeCategory -> {
                                        vm1.deleteIncomeCategory(incomeCategory);
                                    });
                                } else {
                                    l.observe(getViewLifecycleOwner(),limitation -> {
                                        if (limitation != null)
                                        {
                                            lvm.deleteLimitation(limitation);
                                        }
                                    });
                                    sc.observe(getViewLifecycleOwner(), spendingCategory -> {
                                        vm2.deleteSpendingCategory(spendingCategory);
                                    });
                                }
                                Bundle b = new Bundle();
                                b.putBoolean("switchLeft", switchLeft);
                                b.putBoolean("editMode", true);
                                Navigation.findNavController(v).navigate(R.id.action_updateCategoryForm_to_categories_nav,b);
                            }
                        })
                .setNegativeButton("No",null);
                builder2.create().show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}