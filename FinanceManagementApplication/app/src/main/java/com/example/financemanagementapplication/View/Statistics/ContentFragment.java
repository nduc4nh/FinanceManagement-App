package com.example.financemanagementapplication.View.Statistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financemanagementapplication.R;
import com.example.financemanagementapplication.tool.ChartTool;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> label= new ArrayList<>();
    private ArrayList<Long> yIncome= new ArrayList<>();
    private ArrayList<Long> ySpending= new ArrayList<>();
    private ArrayList<Long> yTotal= new ArrayList<>();
    private int color;
    private boolean categoricalContent = false;
    private boolean isSpending;
    private View view;

    public ContentFragment() {
        // Required empty public constructor
    }
    public ContentFragment(ArrayList<String> label, ArrayList<Long> total, Boolean isSpending) {
        this.label = label;
        this.yTotal = total;
        this.isSpending = isSpending;
        this.categoricalContent = true;
        // Required empty public constructor
    }

    public ContentFragment(ArrayList<String> label, ArrayList<Long> yIncome, ArrayList<Long> ySpending, ArrayList<Long> yTotal) {
        this.label = label;
        this.yIncome = yIncome;
        this.ySpending = ySpending;
        this.yTotal = yTotal;
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentFragment newInstance(String param1, String param2) {
        ContentFragment fragment = new ContentFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_content, container, false);
        BarChart chart = view.findViewById(R.id.contentBarchart);
        HorizontalBarChart chart2 = view.findViewById(R.id.contentHorizontalBarchart);
        if (yIncome == null || yTotal == null || ySpending == null || label == null) return view;
        if (!categoricalContent)
        {
            ChartTool.createGroupBarChart(chart,label,yIncome,ySpending,yTotal,getResources(),"Income","Spending","Total");
            chart.invalidate();
        }
        else
        {
            chart.setVisibility(View.GONE);
            chart2.setVisibility(View.VISIBLE);
            if (isSpending) ChartTool.createHorizontalBarChart(chart2,label,yTotal,"Spending",getResources(),R.color.red);
            else ChartTool.createHorizontalBarChart(chart2,label,yTotal,"Income",getResources(),R.color.green);
            chart2.invalidate();
        }

        return view;
    }
}