package com.example.financemanagementapplication.View.Transactions;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financemanagementapplication.DatetimeDialog;
import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.dialog.CalendarDialog;
import com.example.financemanagementapplication.View.Transactions.dialog.PeriodSpendingDialog;
import com.example.financemanagementapplication.model.database.entity.User;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.UserVM;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.DateTool;
import com.example.financemanagementapplication.tool.SupportToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment implements PeriodSpendingDialog.PeriodSpendingDialogListener
        , DatetimeDialog.DatetimeDialogListener, CalendarDialog.CalendarDialogListener {

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
    private UserVM uvm;
    private Toolbar tb;
    private SpendingList spendingList ;
    private IncomeList incomeList;
    private View view;
    private final Long USERID = 0l;
    private LiveData<User> us;
    private String currentDate = DateTool.today();
    private Boolean alltime = false;
    private TextView dateName;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
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
        tb.setTitle("Transactions");

        Bundle bundle = getArguments();
        if (bundle != null) switchLeft = bundle.getBoolean("switchLeft_1");
        if (bundle != null) {
            editMode = bundle.getBoolean("editMode_1");
            alltime = bundle.getBoolean("alltime");
        }
        if (switchLeft) tb.setTitle("Income");
        else tb.setTitle("Spending");
        uvm = new ViewModelProvider(requireActivity()).get(UserVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("START","-------------------------------------------------------------");
        Log.d("alltime",""+alltime);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transactions, container, false);
        FloatingActionButton buttonCreate = view.findViewById(R.id.button_create_transactions);
        FloatingActionButton buttonEdit = view.findViewById(R.id.button_edit_transactions);
        Switch switchItem = view.findViewById(R.id.switch_transactions);
        TextView currentAmount = view.findViewById(R.id.transaction_current_amount);
        dateName = view.findViewById(R.id.transaction_date);
        dateName.setText(currentDate);
        Button buttonYesterdayDate = view.findViewById(R.id.button_backward_date);
        Button buttonTomorrowDate = view.findViewById(R.id.button_forward_date);
        Button buttonPeriodSpending = view.findViewById(R.id.button_period_list);
        Button buttonDatetime = view.findViewById(R.id.button_date_control);

        uvm.setId(0l);
        uvm.tmp.observe(getViewLifecycleOwner(), user -> {
            currentAmount.setText(Converter.rawToReadable(user.getBalance()));
        });
        buttonDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alltime = false;
                DatetimeDialog dt = new DatetimeDialog();
                dt.setTargetFragment(TransactionsFragment.this,0);
                dt.show(getParentFragmentManager(),null);
            }
        });
        buttonPeriodSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeriodSpendingDialog dialog = new PeriodSpendingDialog(getContext(),getViewLifecycleOwner());
                dialog.setTargetFragment(TransactionsFragment.this,0);
                dialog.show(getParentFragmentManager(),null);
            }
        });
        switchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if (!alltime) incomeList = new IncomeList(editMode,isChecked,currentDate);
                    else incomeList = new IncomeList(editMode,isChecked,currentDate,true);
                    tb.setTitle("Income");

                    Toast t = Toast.makeText(getContext(),"Income", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(1000);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container, incomeList).commit();
                }
                else {
                    tb.setTitle("Spending");
                    if (!alltime) spendingList = new SpendingList(editMode,isChecked,currentDate);
                    else spendingList = new SpendingList(editMode,isChecked,currentDate,true);

                    Toast t = Toast.makeText(getContext(),"Spending", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,300);
                    SupportToast sp = new SupportToast(t);
                    sp.showToast(1000);

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container, spendingList).commit();
                }
                buttonCreate.setEnabled(!editMode);
                switchLeft = isChecked;
            }
        });
        switchItem.setChecked(switchLeft);
        if (!alltime) {
            if (switchLeft) {
                //curr = new IncomeCategoriesList(editMode);
                incomeList = new IncomeList(editMode, switchLeft, currentDate);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container, incomeList).commit();
            } else {
                //curr = new SpendingCategoriesList(editMode);
                spendingList = new SpendingList(editMode, switchLeft, currentDate);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container, spendingList).commit();
            }
        }
        else
        {
            dateName.setText("All Time");
            if (switchLeft)
            {
                //curr = new IncomeCategoriesList(editMode);
                incomeList = new IncomeList(editMode,switchLeft,currentDate,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();
            }
            else
            {
                //curr = new SpendingCategoriesList(editMode);
                spendingList = new SpendingList(editMode,switchLeft,currentDate,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
            }
        }
        buttonTomorrowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alltime = false;
                currentDate = DateTool.tomorrow(currentDate);
                dateName.setText(currentDate);
                Log.d("date ->", currentDate);

                if (switchLeft)
                {
                    //curr = new IncomeCategoriesList(editMode);
                    incomeList = new IncomeList(editMode,switchLeft,currentDate);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();
                }
                else
                {
                    //curr = new SpendingCategoriesList(editMode);
                    spendingList = new SpendingList(editMode,switchLeft,currentDate);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
                }
            }
        });

        buttonYesterdayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alltime = false;
                currentDate = DateTool.yesterday(currentDate);
                dateName.setText(currentDate);
                Log.d("<- date", currentDate);
                if (switchLeft)
                {
                    //curr = new IncomeCategoriesList(editMode);
                    incomeList = new IncomeList(editMode,switchLeft,currentDate);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();
                }
                else
                {
                    //curr = new SpendingCategoriesList(editMode);
                    spendingList = new SpendingList(editMode,switchLeft,currentDate);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
                }
            }
        });

        buttonCreate.setEnabled(!editMode);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft_1", switchItem.isChecked());
                b.putLong("USERID", USERID);
                b.putBoolean("alltime",alltime);
                Navigation.findNavController(v).navigate(R.id.action_transaction_nav_to_createTransactionForm,b);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("switchLeft", switchItem.isChecked());
                editMode = !editMode;
                setListState(editMode,alltime);
                buttonCreate.setEnabled(!editMode);
                Toast t = Toast.makeText(getContext(),"Edit "+(editMode?"Enabled":"Disabled"),Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                SupportToast sp = new SupportToast(t);
                sp.showToast(500);
            }
        });

        return view;
    }

    public void setListState(boolean editMode,Boolean alltime)
    {
        if (switchLeft)
        {
            incomeList.update(editMode,alltime);
        }
        else
        {
            spendingList.update(editMode,alltime);
        }
    }

    @Override
    public void chooseAllTime(DialogFragment dialog) {
        alltime = true;
        dateName.setText("All Time");
        if (switchLeft)
        {
            //curr = new IncomeCategoriesList(editMode);
            incomeList = new IncomeList(editMode,switchLeft,currentDate,true);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();

        }
        else
        {
            //curr = new SpendingCategoriesList(editMode);
            spendingList = new SpendingList(editMode,switchLeft,currentDate,true);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
        }
        dialog.dismiss();
    }

    @Override
    public void chooseToday(DialogFragment dialog) {
        currentDate = DateTool.today();
        dateName.setText(currentDate);
        if (switchLeft)
        {
            //curr = new IncomeCategoriesList(editMode);
            incomeList = new IncomeList(editMode,switchLeft,currentDate);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();
        }
        else
        {
            //curr = new SpendingCategoriesList(editMode);
            spendingList = new SpendingList(editMode,switchLeft,currentDate);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
        }
        dialog.dismiss();
    }

    @Override
    public void chooseSelectDay(DialogFragment dialog) {
            CalendarDialog cd = new CalendarDialog(true);
            cd.setTargetFragment(TransactionsFragment.this,0);
            cd.show(getParentFragmentManager(),null);
            dialog.dismiss();
    }

    @Override
    public void chooseThisMonth(DialogFragment dialog) {

    }

    @Override
    public void onCalendarClick(DialogFragment dialog, int year, int month, int day) {
        LocalDate date = LocalDate.of(year,month,day);
        currentDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        dateName.setText(currentDate);
        if (switchLeft)
        {
            //curr = new IncomeCategoriesList(editMode);
            incomeList = new IncomeList(editMode,switchLeft,currentDate);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,incomeList).commit();
        }
        else
        {
            //curr = new SpendingCategoriesList(editMode);
            spendingList = new SpendingList(editMode,switchLeft,currentDate);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactions_container,spendingList).commit();
        }
        dialog.dismiss();
    }
    //no use
    @Override
    public void onCheckPeriod(DialogFragment dialog, boolean checked) {

    }
}