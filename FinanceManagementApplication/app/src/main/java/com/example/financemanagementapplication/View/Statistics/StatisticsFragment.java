package com.example.financemanagementapplication.View.Statistics;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.View.Transactions.IncomeList;
import com.example.financemanagementapplication.model.database.entity.Income;
import com.example.financemanagementapplication.model.database.entity.IncomeCategory;
import com.example.financemanagementapplication.model.database.entity.Spending;
import com.example.financemanagementapplication.model.database.entity.SpendingCategory;
import com.example.financemanagementapplication.model.viewmodel.IncomeCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.IncomeVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingCategoryVM;
import com.example.financemanagementapplication.model.viewmodel.SpendingVM;
import com.example.financemanagementapplication.tool.ChartTool;
import com.example.financemanagementapplication.tool.Converter;
import com.example.financemanagementapplication.tool.DateTool;
import com.example.financemanagementapplication.tool.OnSwipeTouchListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.financemanagementapplication.model.database.entity.Income.mergeByDay;
import static com.example.financemanagementapplication.model.database.entity.Income.mergeByMonth;
import static com.example.financemanagementapplication.tool.DateTool.getDaysInMonth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String vl;
    private String od;
    private IncomeVM ivm;
    private SpendingVM svm;
    private IncomeCategoryVM icvm;
    private SpendingCategoryVM scvm;
    private View view;
    ArrayList<String> daysOut;
    ArrayList<Long> amountsOut;
    private Boolean isLeft = true;
    private Boolean swipedLeft = true;
    private ArrayList<String> mode = new ArrayList<>();
    private Integer curModeInt = 0;
    private String month = DateTool.convertMonth(""+DateTool.parseString(DateTool.today()).getMonthValue()); // characters
    private String year =""+ DateTool.parseString(DateTool.today()).getYear();
    private Toolbar tb;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        tb.setTitle("Statistic");
        ivm = new ViewModelProvider(requireActivity()).get(IncomeVM.class);
        svm = new ViewModelProvider(requireActivity()).get(SpendingVM.class);
        icvm = new ViewModelProvider(requireActivity()).get(IncomeCategoryVM.class);
        scvm = new ViewModelProvider(requireActivity()).get(SpendingCategoryVM.class);
        mode.add("Days");
        mode.add("Months");
        mode.add("Years");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        Button leftBtn = view.findViewById(R.id.stat_left);
        Button rightBtn = view.findViewById(R.id.stat_right);
        Button leftModeBtn = view.findViewById(R.id.stat_move_left_btn);
        Button rightModeBtn = view.findViewById(R.id.stat_move_right_btn);
        TextView curModeTv = view.findViewById(R.id.stat_cur_mode_tv);
        TextView curTimeUnitTv = view.findViewById(R.id.stat_time_unit_tv);
        TextView tvi = view.findViewById(R.id.stat_today_income);
        TextView tvs = view.findViewById(R.id.stat_today_spending);
        TextView tvCate = view.findViewById(R.id.stat_category_name);
        curModeTv.setText(mode.get(curModeInt));
        FrameLayout fl = view.findViewById(R.id.stat_content_fragment_2);

        ivm.getIncomeByDateAlike(DateTool.today()).observe(getViewLifecycleOwner(), incomes -> {
            Log.d("incomes",incomes.toString());
            tvi.setText(Converter.rawToReadable(Income.computeAmount(incomes)));
        });
        svm.getSpendingByDateAlike(DateTool.today()).observe(getViewLifecycleOwner(), spendings -> {
            tvs.setText(Converter.rawToReadable(Spending.computeAmount(spendings)));
            Log.d("spendings",spendings.toString());
        });
        view.setOnTouchListener(new OnSwipeTouchListener(requireContext()){
            public void onSwipeTop() {
                Toast.makeText(requireContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                if (curModeInt == 0) {
                    showIncomeChartByMonth();
                }
                else if (curModeInt == 1) {
                    showIncomeChartByYear();
                }
                else {
                    showIncomeChartByOverall();
                }
                tvCate.setText("Income Category");
                Toast.makeText(requireContext(), "Switched to Income", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                if (curModeInt == 0) {
                    showSpendingChartByMonth();
                }
                else if (curModeInt == 1) {
                    showSpendingChartByYear();
                }
                else {
                    showSpendingChartByOverall();
                }
                tvCate.setText("Spending Category");
                Toast.makeText(requireContext(), "Switched to Spending", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(requireContext(), "bottom", Toast.LENGTH_SHORT).show();
            }
        });

        if (curModeInt == 0) {
            labelTimeUnitByMonth(curTimeUnitTv);
            showChartByMonth();
            showIncomeChartByMonth();
        }
        else if (curModeInt == 1) {
            labelTimeUnitByYear(curTimeUnitTv);
            showChartByYear();
            showIncomeChartByYear();
        }
        else {
            labelTimeUnitAlltime(curTimeUnitTv);
            showChartByOverAll();
            showIncomeChartByOverall();
        }

        leftModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDate();
                tvCate.setText("Income Category");
                if (curModeInt == 0){
                    curModeInt = 2;
                }
                else
                {
                    curModeInt -= 1;
                }
                curModeTv.setText(mode.get(curModeInt));
                if (curModeInt == 0) {
                    labelTimeUnitByMonth(curTimeUnitTv);
                    showChartByMonth();
                    showIncomeChartByMonth();
                }
                else if (curModeInt == 1) {
                    labelTimeUnitByYear(curTimeUnitTv);
                    showChartByYear();
                    showIncomeChartByYear();
                }
                else {
                    labelTimeUnitAlltime(curTimeUnitTv);
                    showChartByOverAll();
                    showIncomeChartByOverall();
                }
            }
        });

        rightModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDate();
                if (curModeInt == 2){
                    curModeInt = 0;
                }
                else
                {
                    curModeInt += 1;
                }
                curModeTv.setText(mode.get(curModeInt));
                if (curModeInt == 0) {
                    labelTimeUnitByMonth(curTimeUnitTv);
                    showChartByMonth();showIncomeChartByMonth();
                }
                else if (curModeInt == 1) {
                    labelTimeUnitByYear(curTimeUnitTv);
                    showChartByYear();showIncomeChartByYear();
                }
                else {
                    labelTimeUnitAlltime(curTimeUnitTv);
                    showChartByOverAll();showIncomeChartByOverall();
                }
            }
        });

        HorizontalBarChart barChart2 = view.findViewById(R.id.testBarchart2);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curModeInt == 0) {
                    minusMonth();
                    labelTimeUnitByMonth(curTimeUnitTv);
                    showChartByMonth();showIncomeChartByMonth();
                }
                else if (curModeInt == 1) {
                    year = String.valueOf(Integer.valueOf(year) - 1);
                    labelTimeUnitByYear(curTimeUnitTv);
                    showChartByYear();showIncomeChartByYear();
                }
                else {
                    labelTimeUnitAlltime(curTimeUnitTv);
                    showChartByOverAll();showIncomeChartByOverall();
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curModeInt == 0) {
                    plusMonth();
                    labelTimeUnitByMonth(curTimeUnitTv);
                    showChartByMonth();showIncomeChartByMonth();
                }
                else if (curModeInt == 1) {
                    year = String.valueOf(Integer.valueOf(year) + 1);
                    labelTimeUnitByYear(curTimeUnitTv);
                    showChartByYear();showIncomeChartByYear();
                }
                else {
                    labelTimeUnitAlltime(curTimeUnitTv);
                    showChartByOverAll();showIncomeChartByOverall();
                }
            }
        });

        barChart2.setOnTouchListener(new OnSwipeTouchListener(requireContext()){
            public void onSwipeTop() {
                Toast.makeText(requireContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                showIncomeCateChart(barChart2);
            }
            public void onSwipeLeft() {
                showSpendingCateChart(barChart2);
            }
            public void onSwipeBottom() {
                Toast.makeText(requireContext(), "bottom", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void showIncomeCateChart(HorizontalBarChart barChart2)
    {
        LiveData<List<IncomeCategory>> ic = icvm.getIncomeCategories();
        ic.observe(getViewLifecycleOwner(), incomeCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (IncomeCategory ele:incomeCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Income>> allIncome = ivm.getIncomes();
            allIncome.observe(getViewLifecycleOwner(), incomes -> {
                HashMap<Long,Long> hm = Income.mergeByCategory(incomes);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ChartTool.createHorizontalBarChart(barChart2,listOfString,y,"Category",getResources(),R.color.green);
                barChart2.invalidate();
            });
        });
    }
    public void showSpendingCateChart(HorizontalBarChart barChart2)
    {
        LiveData<List<SpendingCategory>> sc = scvm.getSpendingCategories();
        sc.observe(getViewLifecycleOwner(), spendingCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (SpendingCategory ele:spendingCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Spending>> allSpending = svm.getSpendings();
            allSpending.observe(getViewLifecycleOwner(), spendings -> {
                HashMap<Long,Long> hm = Spending.mergeByCategory(spendings);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ChartTool.createHorizontalBarChart(barChart2,listOfString,y,"Category",getResources(),R.color.red);
                barChart2.invalidate();
            });
        });
    }

    public void resetDate()
    {
        month = DateTool.convertMonth(""+DateTool.parseString(DateTool.today()).getMonthValue()); // characters
        year =""+ DateTool.parseString(DateTool.today()).getYear();
    }

    public void minusMonth()
    {
        int monthInt = Integer.valueOf(DateTool.inverseMonth(month));
        String monthStr = "";
        int yearInt = Integer.valueOf(year);
        String yearStr = "";

        if (monthInt == 1)
        {
            monthInt = 12;
            yearInt -= 1;
        }
        else
        {
            monthInt -= 1;
        }
        monthStr = DateTool.convertMonth(""+monthInt);
        yearStr = ""+yearInt;
        month = monthStr;
        year = yearStr;
    }

    public void plusMonth()
    {
        int monthInt = Integer.valueOf(DateTool.inverseMonth(month));
        String monthStr = "";
        int yearInt = Integer.valueOf(year);
        String yearStr = "";

        if (monthInt == 12)
        {
            monthInt = 1;
            yearInt += 1;
        }
        else
        {
            monthInt += 1;
        }
        monthStr = DateTool.convertMonth(""+monthInt);
        yearStr = ""+yearInt;

        month = monthStr;
        year = yearStr;
    }

    public void labelTimeUnitByMonth(TextView tv)
    {
        tv.setText(month +", " + year);
    }

    public void labelTimeUnitByYear(TextView tv)
    {
        tv.setText(year);
    }

    public void labelTimeUnitAlltime(TextView tv)
    {
        tv.setText("All time");
    }

    public void showChartByMonth()
    {
        LiveData<List<Income>> inMonth = ivm.getIncomeByDateAlike(month +" %, "+year);
        LiveData<List<Spending>> inMonthS = svm.getSpendingByDateAlike(month +" %, "+year);
        inMonth.observe(getViewLifecycleOwner(), incomes -> {
            Log.d("alo",""+incomes.size());
            HashMap<String, ArrayList<Income>> hm = Income.mergeByDay(incomes);
            ArrayList<String> days = DateTool.getDaysInMonth(month);
            ArrayList<Long> amounts = new ArrayList<>();
            for (String ele:days)
            {
                if (hm.containsKey(ele)){
                    amounts.add(Income.computeAmount(hm.get(ele)));
                }
                else
                {
                    amounts.add(0l);
                }
            }
            daysOut = days;
            amountsOut = amounts;
            inMonthS.observe(getViewLifecycleOwner(),spendings -> {
                HashMap<String, ArrayList<Spending>> hmS = Spending.mergeByDay(spendings);
                ArrayList<String> daysS = DateTool.getDaysInMonth(month);
                ArrayList<Long> amountsS = new ArrayList<>();
                ArrayList<Long> totalAmountsS = new ArrayList<>();
                for (String ele:daysS)
                {
                    if (hmS.containsKey(ele)){
                        amountsS.add(Spending.computeAmount(hmS.get(ele)));
                    }
                    else
                    {
                        amountsS.add(0l);
                    }
                }
                int n = days.size();
                for (int i = 0; i < n;++i) totalAmountsS.add(amounts.get(i) - amountsS.get(i));
                ContentFragment content = new ContentFragment(days,amounts,amountsS,totalAmountsS);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment,content).commit();
            });
        });
        //isLeft = !isLeft;
        //leftBtn.setEnabled(isLeft);
        //rightBtn.setEnabled(!isLeft);
    }

    public void showIncomeChartByMonth()
    {
        LiveData<List<IncomeCategory>> ic = icvm.getIncomeCategories();
        ic.observe(getViewLifecycleOwner(), incomeCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (IncomeCategory ele:incomeCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Income>> allIncome = ivm.getIncomeByDateAlike(month +" %, "+year);
            allIncome.observe(getViewLifecycleOwner(), incomes -> {
                HashMap<Long,Long> hm = Income.mergeByCategory(incomes);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,false);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }

    public void showSpendingChartByMonth()
    {
        LiveData<List<SpendingCategory>> sc = scvm.getSpendingCategories();
        sc.observe(getViewLifecycleOwner(), spendingCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (SpendingCategory ele:spendingCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Spending>> allSpending = svm.getSpendingByDateAlike(month +" %, "+year);
            allSpending.observe(getViewLifecycleOwner(), spendings -> {
                HashMap<Long,Long> hm = Spending.mergeByCategory(spendings);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }

    public void showChartByYear()
    {
        LiveData<List<Income>> inYear = ivm.getIncomeByDateAlike("% %, "+year);
        LiveData<List<Spending>> inYearS = svm.getSpendingByDateAlike("% %, "+year);
        inYear.observe(getViewLifecycleOwner(), incomes -> {
            HashMap<String, ArrayList<Income>> hm = Income.mergeByMonth(incomes);
            ArrayList<String> months = DateTool.getMonthsInYear();
            ArrayList<Long> amounts = new ArrayList<>();
            for (String ele:months)
            {
                if (hm.containsKey(ele)){
                    amounts.add(Income.computeAmount(hm.get(ele)));
                }
                else
                {
                    amounts.add(0l);
                }
            }
            inYearS.observe(getViewLifecycleOwner(),spendings -> {
                HashMap<String, ArrayList<Spending>> hmS = Spending.mergeByMonth(spendings);
                ArrayList<String> monthsS = DateTool.getMonthsInYear();
                ArrayList<Long> amountsS = new ArrayList<>();
                ArrayList<Long> totalAmountsS = new ArrayList<>();
                for (String ele:monthsS)
                {
                    if (hmS.containsKey(ele)){
                        amountsS.add(Spending.computeAmount(hmS.get(ele)));
                    }
                    else
                    {
                        amountsS.add(0l);
                    }
                }
                int n = months.size();
                for (int i = 0; i < n;++i) totalAmountsS.add(amounts.get(i) - amountsS.get(i));
                ContentFragment content = new ContentFragment(months,amounts,amountsS,totalAmountsS);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment,content).commit();
            });
        });
    }
    public void showIncomeChartByYear()
    {
        LiveData<List<IncomeCategory>> ic = icvm.getIncomeCategories();
        ic.observe(getViewLifecycleOwner(), incomeCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (IncomeCategory ele:incomeCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Income>> allIncome = ivm.getIncomeByDateAlike("% %, "+year);
            allIncome.observe(getViewLifecycleOwner(), incomes -> {
                HashMap<Long,Long> hm = Income.mergeByCategory(incomes);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,false);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }

    public void showSpendingChartByYear()
    {
        LiveData<List<SpendingCategory>> sc = scvm.getSpendingCategories();
        sc.observe(getViewLifecycleOwner(), spendingCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (SpendingCategory ele:spendingCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Spending>> allSpending = svm.getSpendingByDateAlike("% %, "+year);
            allSpending.observe(getViewLifecycleOwner(), spendings -> {
                HashMap<Long,Long> hm = Spending.mergeByCategory(spendings);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }

    public void showChartByOverAll()
    {
        LiveData<List<Income>> inYear = ivm.getIncomes();
        LiveData<List<Spending>> inYearS = svm.getSpendings();

        inYear.observe(getViewLifecycleOwner(), incomes -> {
            int min,max;
            HashMap<String, ArrayList<Income>> hm = Income.mergeByYear(incomes);
            ArrayList<String> months = DateTool.getMonthsInYear();
            min = Income.getMaxYearInSeq(incomes)[0];
            max = Income.getMaxYearInSeq(incomes)[1];

            inYearS.observe(getViewLifecycleOwner(),spendings -> {
                HashMap<String, ArrayList<Spending>> hmS = Spending.mergeByYear(spendings);
                ArrayList<String> years = new ArrayList<>();
                Integer[] tmpS = Spending.getMaxYearInSeq(spendings);
                int minFinal = min < tmpS[0]? min:tmpS[0];
                int maxFinal = max > tmpS[1]? max:tmpS[1];
                ArrayList<Long> amountsS = new ArrayList<>();
                ArrayList<Long> amounts = new ArrayList<>();
                ArrayList<Long> totalAmountsS = new ArrayList<>();
                for (int i = minFinal; i <= maxFinal;++i)
                {
                    String ele = ""+i;
                    years.add(ele);
                    if (hmS.containsKey(ele)){
                        amountsS.add(Spending.computeAmount(hmS.get(ele)));
                    }
                    else
                    {
                        amountsS.add(0l);
                    }
                    if (hm.containsKey(ele)){
                        amounts.add(Income.computeAmount(hm.get(ele)));
                    }
                    else
                    {
                        amounts.add(0l);
                    }
                }

                int n = maxFinal - minFinal + 1;
                for (int i = 0; i < n;++i) totalAmountsS.add(amounts.get(i) - amountsS.get(i));
                Log.d("over all", amounts.toString() + amountsS.toString() + totalAmountsS.toString() + years.toString());
                ContentFragment content = new ContentFragment(years,amounts,amountsS,totalAmountsS);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment,content).commit();
            });
        });
    }

    public void showIncomeChartByOverall()
    {
        LiveData<List<IncomeCategory>> ic = icvm.getIncomeCategories();
        ic.observe(getViewLifecycleOwner(), incomeCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (IncomeCategory ele:incomeCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Income>> allIncome = ivm.getIncomes();
            allIncome.observe(getViewLifecycleOwner(), incomes -> {
                HashMap<Long,Long> hm = Income.mergeByCategory(incomes);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,false);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }

    public void showSpendingChartByOverall()
    {
        LiveData<List<SpendingCategory>> sc = scvm.getSpendingCategories();
        sc.observe(getViewLifecycleOwner(), spendingCategories -> {
            ArrayList<Long> listOfIds = new ArrayList<>();
            ArrayList<String> listOfString = new ArrayList<>();
            for (SpendingCategory ele:spendingCategories) {
                listOfIds.add(ele.getId());
                listOfString.add(ele.getName());
            }
            LiveData<List<Spending>> allSpending = svm.getSpendings();
            allSpending.observe(getViewLifecycleOwner(), spendings -> {
                HashMap<Long,Long> hm = Spending.mergeByCategory(spendings);
                ArrayList<Long> y = new ArrayList<>();
                int n= listOfIds.size();
                for (int i = 0; i < n;++i){
                    Long tmp = listOfIds.get(i);
                    if (hm.containsKey(tmp)) y.add(hm.get(tmp));
                    else y.add(0l);
                }
                ContentFragment content = new ContentFragment(listOfString,y,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.stat_content_fragment_2,content).commit();
            });
        });
    }
}