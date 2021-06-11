package com.example.financemanagementapplication.View.Transactions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.dialog.CalendarDialog;
import com.example.financemanagementapplication.View.Transactions.dialog.SelectDialog;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Limitation;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.database.entity.parents.Category;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.LimitationVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.NumberTextWatcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionInput extends Fragment implements CalendarDialog.CalendarDialogListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] a;
    private String name;
    private String amount;
    private String date;
    private String dateInLabel = "";

    private Boolean switchLeft;
    private Boolean isPeriod = false;
    private EditText nameEt;
    private EditText amountEt;
    private Button cateBtn;
    private Button dateBtn;
    private TextView cateName;
    private TextView dateName;
    private TextView periodName;
    private List<IncomeCategory> inc;
    private List<SpendingCategory> spc;
    private List<Limitation> ls;
    private IncomeCategoryVM ivm;
    private SpendingCategoryVM svm;
    private LimitationVM lvm;
    private SelectDialog select;
    private Long cateId;
    public View view;
    private Map<Long, Limitation> limitationMap;
    private boolean isSpending;
    private boolean isEdit;
    public TransactionInput() {
        // Required empty public constructor
    }
    public TransactionInput(boolean switchLeft) {
        // Required empty public constructor
        this.switchLeft = switchLeft;
    }
    public void setEdit(Boolean edit)
    {
        this.isEdit = edit;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionInput.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionInput newInstance(String param1, String param2) {
        TransactionInput fragment = new TransactionInput();
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
            switchLeft = getArguments().getBoolean("switchleft_1");
        }

        ivm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        lvm = new ViewModelProvider(requireActivity()).get(LimitationVM.class);
    }

    @Override
    public void onResume() {
        Log.d("Resume", " test");
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("START","-------------------------------------------------------------");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transactions_input, container, false);
        nameEt = view.findViewById(R.id.trans_name_edit);
        amountEt = view.findViewById(R.id.trans_amount_edit);
        cateBtn = view.findViewById(R.id.button_choose_cate);
        cateName = view.findViewById(R.id.trans_cate_name);
        dateBtn = view.findViewById(R.id.button_choose_date);
        dateName = view.findViewById(R.id.trans_date_name);
        periodName = view.findViewById(R.id.period_label);

        limitationMap = new HashMap<Long,Limitation>();
        LiveData<List<IncomeCategory>> ic = ivm.getIncomeCategories();
        LiveData<List<SpendingCategory>> sc = svm.getSpendingCategories();
        LiveData<List<Limitation>> ll = lvm.getLimitations();

        try{
            amountEt.addTextChangedListener(new NumberTextWatcher(amountEt));
        }catch (Exception e) {

        }
        if (isEdit)
        {
            dateBtn.setEnabled(false);
            cateBtn.setEnabled(false);
        }

        if (switchLeft)
        {
            ic.observe(getViewLifecycleOwner(),incomeCategories -> {
                int n = incomeCategories.size();
                a = new String[n];
                inc = incomeCategories;
                for (int i = 0; i < n; ++i) a[i] = incomeCategories.get(i).getName();
            });
        }
        else {
            sc.observe(getViewLifecycleOwner(),spendingCategories -> {
                int n = spendingCategories.size();
                a = new String[n];
                spc = spendingCategories;
                for (int i = 0; i < n; ++i) a[i] = spendingCategories.get(i).getName();
            });

            ll.observe(getViewLifecycleOwner(),limitations -> {
                for (Limitation ele:limitations) limitationMap.put(ele.getId(),ele);
            });
        }
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDialog cd = new CalendarDialog(switchLeft);
                cd.setTargetFragment(TransactionInput.this,0);
                cd.show(getParentFragmentManager(),null);
            }
        });
        cateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*select = new SelectDialog(switchLeft,getViewLifecycleOwner());
                select.show(getParentFragmentManager(),"");*/
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getActivity());
                mbuilder.setSingleChoiceItems(a, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cateName.setText(a[which]);
                        cateName.setTextColor(getResources().getColor(R.color.green));
                        if (switchLeft) cateId = inc.get(which).getId();
                        else cateId = spc.get(which).getId();
                        Log.d("SELECTED CATE ID", String.valueOf(cateId));
                    }
                });
                mbuilder.show();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Log.d("nameEt", String.valueOf(nameEt == null));
        return view;
    }

    public String getName()
    {
        name = nameEt.getText() == null?"":nameEt.getText().toString();
        return name;
    }
    public String getAmount()
    {
        amount = amountEt.getText() == null?"":amountEt.getText().toString();
        return amount;
    }
    public String getDate()
    {
        date = dateName.getText() == null?"Unknown":dateName.getText().toString();
        return date;
    }

    public void setName(String name)
    {
        nameEt.setText(name);
        nameEt.invalidate();
    }
    public void setAmount(Long amount)
    {
        amountEt.setText(Converter.rawToReadable(amount));
        amountEt.invalidate();
    }

    public void setCateName(String name)
    {
        cateName.setText(name);
        cateName.invalidate();
    }
    public void setSwitchLeft(Boolean s)
    {
        this.switchLeft = s;
    }

    public Long getCateId(){
        //Log.d("test",String.valueOf(month));
        return cateId;
    }

    public void setCateAvailableColor()
    {
        cateName.setTextColor(view.getResources().getColor(R.color.ordinary));
        cateName.invalidate();
    }
    public void setDateAvailableColor()
    {
        dateName.setTextColor(view.getResources().getColor(R.color.ordinary));
        dateName.invalidate();
    }
    public void setCateInactiveColor()
    {
        cateName.setTextColor(view.getResources().getColor(R.color.red));
        cateName.invalidate();
    }
    public void setDateName(String date)
    {
        dateName.setText(date);
        dateName.invalidate();
    }
    @Override
    public void onCalendarClick(DialogFragment dialog,int year, int month, int day) {
        LocalDate date = LocalDate.of(year,month,day);
        dateInLabel = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        dateName.setText(dateInLabel);
        dateName.setTextColor(getResources().getColor(R.color.green));

    }

    @Override
    public void onCheckPeriod(DialogFragment dialog, boolean checked) {
        if (checked) {
            periodName.setText("Monthly Transaction*");
            isPeriod = true;
        }
        else {
            periodName.setText("");
            isPeriod = false;
        }
    }
    public Boolean getIsPeriod() {return isPeriod;}

    public Limitation getLimitation(Long cateId) {
        return limitationMap.get(cateId);
    }

    public View getView()
    {
        return view;
    }

    public void disableAmountInput()
    {
        amountEt.setEnabled(false);
    }
}
