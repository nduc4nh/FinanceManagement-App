package com.example.financemanagementapplication.View.Transactions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Categories.CategoryInput;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.IncomeVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingVM;
import com.example.financemanagementapplication.model.viewmodel.UserVM;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.SupportToast;

public class updateTransactionForm extends Fragment {
    private IncomeVM vm1;
    private SpendingVM vm2;
    public Long id;
    private LiveData<Income> ic;
    private LiveData<Spending> sc;
    private User tmpUser;
    public updateTransactionForm() {
    }
    private TransactionInput tmp;
    private Income tmpIncome;
    private Spending tmpSpending;
    private UserVM uvm;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private LimitationVM lvm;
    private String oldCateName;
    private boolean switchLeft;
    private Long oldAmount;
    private String mess;
    private Long oldCateId;
    private Limitation limit;
    private boolean alltime;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar tb = getActivity().findViewById(R.id.toolbar);
        switchLeft = getArguments().getString("type").equals("income");
        alltime = getArguments().getBoolean("alltime");
        tb.setTitle("Edit " + getArguments().getString("type"));
        id = getArguments().getLong("id");
        oldCateName = getArguments().getString("cate_name");
        Log.d("ID", String.valueOf(id));
        vm1 =  new ViewModelProvider(requireActivity()).get(IncomeVM.class);
        vm2 = new ViewModelProvider(requireActivity()).get(SpendingVM.class);
        uvm = new ViewModelProvider(requireActivity()).get(UserVM.class);
        ivm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        lvm = new ViewModelProvider(requireActivity()).get(LimitationVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form,container,false);
        Log.d("START","-------------------------------------------------------------");
        Button backBtn = view.findViewById(R.id.button_back);
        Button deleteBtn = view.findViewById(R.id.button_delete);
        Button updateBtn = view.findViewById(R.id.button_save);
        deleteBtn.setVisibility(View.VISIBLE);

        tmp = new TransactionInput(switchLeft);
        tmp.setEdit(true);

        uvm.tmp.observe(getViewLifecycleOwner(), user -> {
            tmpUser = user;
        });

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.form_container,tmp);
        transaction.commit();
        //TableRow limitRow = cur.getView().findViewById(R.id.limit_row_cate);

        if (switchLeft) {
            vm1.setId(id);
            try{

            ic = vm1.getIncomeById(id);
            Log.d("1",String.valueOf(id));
            ic.observe(getViewLifecycleOwner(),income -> {
                if (tmp.getView() != null){
                tmp.setName(income.getName());
                tmp.setAmount(income.getAmount());
                tmp.setCateName(oldCateName);
                if (oldCateName.equals("Conservation")) tmp.disableAmountInput();
                tmp.setDateName(income.getDate());
                tmp.setCateAvailableColor();
                tmp.setDateAvailableColor();}
                else{
                    Bundle b = new Bundle();
                    b.putString("type", "income");
                    b.putLong("id", id);
                    b.putString("cate_name",oldCateName);
                    Navigation.findNavController(view).navigate(R.id.action_updateTransactionForm_self ,b);
                }
            });}
            catch(Exception e)
            {

            }

            Log.d("CHECK NULL", String.valueOf(tmpIncome == null));

            //ic.removeObservers(getViewLifecycleOwner());
        }
        else {
            Log.d("1","update spending");
            vm2.setId(id);
            sc = vm2.getSpendingById(id);
            sc.observe(getViewLifecycleOwner(),spending -> {
                if (tmp.getView() != null){
                tmp.setName(spending.getName());
                tmp.setAmount(spending.getAmount());
                tmp.setCateName(oldCateName);
                if (oldCateName.equals("Conservation")) tmp.disableAmountInput();
                tmp.setDateName(spending.getDate());
                tmp.setCateAvailableColor();
                tmp.setDateAvailableColor();}
                else{
                    Bundle b = new Bundle();
                    b.putString("type", "spending");
                    b.putLong("id", id);
                    b.putString("cate_name",oldCateName);
                    Navigation.findNavController(view).navigate(R.id.action_updateTransactionForm_self ,b);
                }
            });
            //sc.removeObservers(getViewLifecycleOwner());
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft", switchLeft);
                b.putBoolean("editMode", true);
                b.putBoolean("alltime",alltime);
                Navigation.findNavController(v).navigate(R.id.action_updateTransactionForm_to_transaction_nav ,b);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAmount = tmp.getAmount();
                String newName = tmp.getName();
                Long newCate = tmp.getCateId();
                String newDate = tmp.getDate();
                Log.d("CateID", String.valueOf(newCate));
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                if (newAmount.isEmpty()){
                    Toast t = Toast.makeText(getContext(), "Abort! Enter transaction's amount",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(800);
                }
                else{
                    Long rawAmount = Converter.readableToRaw(newAmount);
                    mess = "Are you sure to create this transaction?\n";
                    if(!switchLeft){
                        sc.observe(getViewLifecycleOwner(), spending -> {
                            Long cateId = spending.getCategoryId();
                            limit = tmp.getLimitation(cateId);
                            if (limit != null) {
                                Limitation tmpl = limit.copy();
                                String oldlm = tmpl.getPercentage();
                                tmpl.setCurrent(tmpl.getCurrent() - spending.getAmount() + rawAmount);
                                String newlm = tmpl.getPercentage();
                                mess = ""+ oldlm + " to " + newlm + " of the maximum limit\n" + mess;
                            }
                        });
                    }
                    builder2.setTitle("Confirmation").setMessage(mess +
                            " Amount: " +newAmount).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Long readableAmount = Converter.readableToRaw(newAmount);
                            if (switchLeft) {
                                ic.observe(getViewLifecycleOwner(), income -> {
                                    oldAmount = income.getAmount();
                                    income.setName(newName);
                                    income.setAmount(readableAmount);
                                    if (newCate != null) income.setCategoryId(newCate);
                                    income.setDate(newDate);
                                    tmpUser.setBalance(tmpUser.getBalance() - oldAmount + income.getAmount());
                                    uvm.updateUser(tmpUser);
                                    vm1.updateIncome(income);
                                });
                            } else {
                                sc.observe(getViewLifecycleOwner(), spending -> {
                                    oldAmount = spending.getAmount();
                                    spending.setName(newName);
                                    spending.setAmount(readableAmount);
                                    if (newCate != null) spending.setCategoryId(newCate);
                                    spending.setDate(newDate);
                                    tmpUser.setBalance(tmpUser.getBalance() + oldAmount - spending.getAmount());
                                    if (!(limit==null))
                                    {
                                        limit.setCurrent(limit.getCurrent() - oldAmount + rawAmount);
                                        lvm.updateLimitation(limit);
                                    }
                                    uvm.updateUser(tmpUser);
                                    vm2.updateSpending(spending);
                                });

                            }
                            Bundle b = new Bundle();
                            b.putBoolean("switchLeft", switchLeft);
                            b.putBoolean("editMode", true);
                            b.putBoolean("alltime",alltime);
                            Navigation.findNavController(v).navigate(R.id.action_updateTransactionForm_to_transaction_nav,b);
                        }
                    }).setNegativeButton("No", null);
                    builder2.create().show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mess = "Are your sure to delete this transaction?";
                if(!switchLeft){
                    sc.observe(getViewLifecycleOwner(), spending -> {
                        Long cateId = spending.getCategoryId();
                        limit = tmp.getLimitation(cateId);
                        if (limit != null) {
                            Limitation tmpl = limit.copy();
                            String oldlm = tmpl.getPercentage();
                            tmpl.setCurrent(tmpl.getCurrent() - spending.getAmount());
                            String newlm = tmpl.getPercentage();
                            mess = ""+ oldlm + " to " + newlm + " of the maximum limit\n" + mess;
                        }
                    });
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                builder2.setTitle("Confirmation").setMessage(mess)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (switchLeft) {
                                    ic.observe(getViewLifecycleOwner(), income -> {
                                        tmpUser.setBalance(tmpUser.getBalance() - income.getAmount());
                                        uvm.updateUser(tmpUser);
                                        vm1.deleteIncome(income);
                                    });
                                } else {
                                    sc.observe(getViewLifecycleOwner(), spending -> {
                                        tmpUser.setBalance(tmpUser.getBalance() + spending.getAmount());
                                        if (limit != null)
                                        {
                                            limit.setCurrent(limit.getCurrent() - spending.getAmount());
                                            lvm.updateLimitation(limit);
                                        }
                                        uvm.updateUser(tmpUser);
                                        vm2.deleteSpending(spending);
                                    });
                                }
                                Bundle b = new Bundle();
                                b.putBoolean("switchLeft", switchLeft);
                                b.putBoolean("editMode", true);
                                b.putBoolean("alltime",alltime);
                                Log.d("alltime",""+alltime);
                                Navigation.findNavController(v).navigate(R.id.action_updateTransactionForm_to_transaction_nav,b);
                            }
                        }).setNegativeButton("No", null);
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
