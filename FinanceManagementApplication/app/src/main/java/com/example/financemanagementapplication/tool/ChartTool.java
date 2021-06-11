package com.example.financemanagementapplication.tool;

import android.content.res.Resources;
import android.util.Log;

import com.example.financemanagementapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChartTool {
    static public void createBarChart(BarChart chart,ArrayList<String> x, ArrayList<Long> y,String name,Resources resources,int color)
    {
        int n = x.size();
        ArrayList<BarEntry> barChartData = new ArrayList<>();
        for (int i = 0; i < n;++i) barChartData.add(new BarEntry(i,y.get(i)));
        BarDataSet data = new BarDataSet(barChartData,name);
        data.setColor(resources.getColor(color));

        BarData officialData = new BarData(data);
        officialData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("###,###,###");
                if (value == 0) return "";
                return mFormat.format(value);
            }
        });
        chart.setFitBars(true);
        chart.setData(officialData);
        chart.animateY(2000);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return x.get((int) value);
            }
        });
        xAxis.setLabelCount(n/2,false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(true);
        chart.getDescription().setText("Barchart");
    }

    static public void createHorizontalBarChart(HorizontalBarChart chart, ArrayList<String> x, ArrayList<Long> y, String name, Resources resources, int color)
    {
        int n = y.size();
        Log.d("ukm", "" + n);
        ArrayList<BarEntry> barChartData = new ArrayList<>();
        for (int i = 0; i < n;++i) barChartData.add(new BarEntry(0.4f + i,y.get(i)));
        BarDataSet data = new BarDataSet(barChartData,name);
        data.setColor(resources.getColor(color));
        BarData officialData = new BarData(data);
        officialData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("###,###,###");
                if (value == 0) return "";
                return mFormat.format(value);
            }
        });
        chart.setData(officialData);
        chart.setFitBars(true);
        chart.animateXY(2000,2000);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        if (n!= 1)xAxis.setAxisMaximum(n==2?n+1:n);
        else xAxis.setAxisMaximum(3);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (((int) (value * 10) % 5 == 0) && ((int) (value * 10) % 10 != 0)){
                    if ((int)(value) < n) return x.get((int) value);
                    else {
                        return "Other";
                    }
                }

                return "";
            }
        });
        chart.setVisibleXRangeMaximum(3);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(true);
        chart.getAxisRight().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) return "";
                String tmp = ""+value;
                DecimalFormat mFormat;
                if (tmp.length() > 6) {
                    return "" + (int) (value/1000000) +"m";
                }
                else if (tmp.length() == 6) return "" + (int)(value/1000) +"k";
                else {
                    mFormat = new DecimalFormat("###,###,###");
                    return mFormat.format(value);
                }
            }
        });
        chart.getDescription().setText("Category chart");
        chart.invalidate();
    }

    static public void createGroupBarChart(BarChart chart,ArrayList<String> x, ArrayList<Long> y1, ArrayList<Long> y2, ArrayList<Long> y3, Resources resources,
                                           String name1, String name2, String name3)
    {
        int n = x.size();
        ArrayList<BarEntry> barChartData1 = new ArrayList<>();
        ArrayList<BarEntry> barChartData2 = new ArrayList<>();
        ArrayList<BarEntry> barChartData3 = new ArrayList<>();
        for (int i = 0; i < n;++i) {
            barChartData1.add(new BarEntry(i,y1.get(i)));
            barChartData2.add(new BarEntry(i,y2.get(i)));
            barChartData3.add(new BarEntry(i,y3.get(i)));
        }
        BarDataSet set1 = new BarDataSet(barChartData1,name1);
        BarDataSet set2 = new BarDataSet(barChartData2,name2);
        BarDataSet set3 = new BarDataSet(barChartData3,name3);
        set1.setColor(resources.getColor(R.color.green));
        set2.setColor(resources.getColor(R.color.red));
        set3.setColor(resources.getColor(R.color.colorPrimary));
        float groupSpace = 0.1f;
        float barSpace = 0.00f; // x2 dataset
        float barWidth = 0.3f; // x2 dataset
        // (0.3 + 0.02) * 3 + 0.04 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2, set3);
        d.setBarWidth(barWidth);
        d.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("###,###,###");
                if (value == 0) return "";
                return mFormat.format(value);
            }
        });
        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0
        chart.setData(d);
        chart.animateXY(2000,2000);
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(n==2?n+1:n);
        chart.setFitBars(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (((int) (value * 10) % 5 == 0) && ((int) (value * 10) % 10 != 0)){
                    if ((int)(value) < n) return x.get((int) value);
                    else {
                        return "Other";
                    }
                }

                return "";
            }
        });
        chart.setVisibleXRangeMaximum(3);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(true);
        chart.getAxisRight().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0) return "";
                String tmp = ""+value;
                DecimalFormat mFormat;
                if (tmp.length() > 6) {
                    return "" + (int) (value/1000000) +"m";
                }
                else if (tmp.length() == 6) return "" + (int)(value/1000) +"k";
                else {
                    mFormat = new DecimalFormat("###,###,###");
                    return mFormat.format(value);
                }
            }
        });
        chart.getDescription().setText("Transaction chart");
    }
}
