package com.example.financemanagementapplication.View.Transactions;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.PeriodPayment;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.IncomeVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.PeriodPaymentVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingVM;
import com.example.financemanagementapplication.model.viewmodel.UserVM;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.DateTool;
import com.example.financemanagementapplication.tool.SupportToast;

import java.util.List;

public class CreateTransactionForm extends Fragment {
    public CreateTransactionForm() {
    }
    private Long USERID;
    private boolean switchLeft;
    private TransactionInput cur;
    private IncomeVM transIvm;
    private SpendingVM transSvm;
    private UserVM userVM;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private PeriodPaymentVM pvm;
    private LimitationVM lvm;
    private LiveData<User> currentUser;
    private LiveData<Limitation> l;
    private User tmp;
    private Limitation tmp2;
    private String mess;
    private Boolean alltime = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar tb = getActivity().findViewById(R.id.toolbar);
        switchLeft = getArguments().getBoolean("switchLeft_1");
        alltime = getArguments().getBoolean("alltime");
        if (switchLeft) tb.setTitle("Create Income");
        else tb.setTitle("Create Spending");

        USERID = getArguments().getLong("USERID");
        ivm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        pvm = new ViewModelProvider(requireActivity()).get(PeriodPaymentVM.class);
        transIvm = new ViewModelProvider(requireActivity()).get(IncomeVM.class);
        transSvm = new ViewModelProvider(requireActivity()).get(SpendingVM.class);
        userVM = new ViewModelProvider(requireActivity()).get(UserVM.class);
        lvm = new ViewModelProvider(requireActivity()).get(LimitationVM.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("START","-------------------------------------------------------------");
        currentUser= userVM.getUserById(USERID);
        View view = inflater.inflate(R.layout.fragment_form,container,false);
        Button backBtn = view.findViewById(R.id.button_back);
        Button updateBtn = view.findViewById(R.id.button_save);
        cur = new TransactionInput(switchLeft);
        userVM.tmp.observe(getViewLifecycleOwner(), user -> {
            tmp = user;
        });

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.form_container,cur).commit();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft_1",switchLeft);
                b.putBoolean("alltime",alltime);
                Navigation.findNavController(v).navigate(R.id.action_createTransactionForm_to_transaction_nav,b);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isPeriod = cur.getIsPeriod();
                String name = cur.getName();
                String amount = cur.getAmount();
                Long cateId = cur.getCateId();
                String date = cur.getDate();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireActivity());
                if(amount.isEmpty()){
                    Toast t = Toast.makeText(getContext(), "Abort! Enter transaction's amount",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(800);
                }
                else if (cateId == null){
                    Toast t = Toast.makeText(getContext(), "Abort! Select a category",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(800);
                }
                else if(date.equals("No date"))
                {
                    Toast t = Toast.makeText(getContext(), "Abort! Enter the date",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(800);
                }
                else {
                    Long rawAmount = Converter.readableToRaw(amount);
                    mess = "Are you sure to create this transaction?\n";
                    if(!switchLeft){
                        Limitation tmpl = cur.getLimitation(cateId);
                        if (tmpl != null) {
                            Limitation tmpll = cur.getLimitation(cateId).copy();
                            String oldAmount = tmpll.getPercentage();
                            tmpll.setCurrent(tmpll.getCurrent() + rawAmount);
                            String newAmount = tmpll.getPercentage();
                            mess = oldAmount + " to " + newAmount + " of the maximum limit\n" + mess;
                        }
                    }
                    builder2.setTitle("Confirmation").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isPeriod && !switchLeft){
                                PeriodPayment tmp = new PeriodPayment(name,rawAmount, DateTool.getMonth(date),cateId,date);
                                pvm.insertPeriodPayment(tmp);
                            }
                            if(switchLeft)
                            {
                                Income ic = new Income(name, rawAmount,USERID,cateId);
                                ic.setDate(date);
                                tmp.setBalance(tmp.getBalance() + rawAmount);
                                userVM.updateUser(tmp);
                                transIvm.insertIncome(ic);
                            }
                            else
                            {
                                Spending sc = new Spending(name,Converter.readableToRaw(amount),USERID,cateId);
                                sc.setDate(date);
                                tmp.setBalance(tmp.getBalance() - rawAmount);
                                userVM.updateUser(tmp);
                                tmp2 = cur.getLimitation(cateId);
                                if (!(tmp2==null))
                                {
                                    tmp2.setCurrent(tmp2.getCurrent() + rawAmount);
                                    lvm.updateLimitation(tmp2);
                                }
                                transSvm.insertSpending(sc);
                            }
                            Bundle b = new Bundle();
                            b.putBoolean("switchLeft_1",switchLeft);
                            b.putBoolean("alltime",alltime);
                            Navigation.findNavController(v).navigate(R.id.action_createTransactionForm_to_transaction_nav,b);
                        }
                    }).setNegativeButton("No",null);
                    builder2.setMessage(mess);
                    builder2.create().show();
                }
            }
        });
        return view;
    }
}
