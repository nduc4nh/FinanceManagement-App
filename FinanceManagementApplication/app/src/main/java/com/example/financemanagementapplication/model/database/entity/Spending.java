package com.example.financemanagementapplication.model.database.entity;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.financemanagementapplication.model.database.entity.parents.Finance;
import com.example.financemanagementapplication.tool.DateTool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Entity(tableName = "spendings")
public class Spending extends Finance {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public Spending(String name, Long amount, Long userId, Long categoryId) {
        super(name, amount, userId, categoryId);
        this.id = new Random().nextLong();
    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        return this.id.toString() +", "+super.toString();
    }
    static public List<Spending> sortByDate(List<Spending> finances)
    {
        ArrayList<Spending> re = new ArrayList<>();
        ArrayList<LocalDate> lcd = new ArrayList<>();
        Map<LocalDate,ArrayList<Spending>> map = new HashMap<>();

        for (Spending ele:finances)
        {
            LocalDate tmp = LocalDate.parse(ele.getDate(), DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            lcd.add(tmp);
            if (!map.containsKey(tmp)){
                ArrayList tmpArr = new ArrayList();
                tmpArr.add(ele);
                map.put(tmp,tmpArr);
            }
            else map.get(tmp).add(ele);

        }
        Collections.sort(lcd);
        String iter = "";
        for (LocalDate ele:lcd) {
            if (iter.equals(ele.toString())) continue;
            iter = ele.toString();
            for (Spending ele2:map.get(ele))
                re.add(ele2);
        }
        return re;
    }
    static public HashMap<String, ArrayList<Spending>> mergeByMonth(List<Spending> finances)
    {
        HashMap<String,ArrayList<Spending>> re = new HashMap<>();
        ArrayList<LocalDate> lcd = new ArrayList<>();
        Map<LocalDate,ArrayList<Spending>> map = new HashMap<>();

        for (Spending ele:finances)
        {
            LocalDate tmp = LocalDate.parse(ele.getDate(), DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            lcd.add(tmp);
            if (!map.containsKey(tmp)){
                ArrayList tmpArr = new ArrayList();
                tmpArr.add(ele);
                map.put(tmp,tmpArr);
            }
            else map.get(tmp).add(ele);
        }
        Collections.sort(lcd);
        String iter = "";
        for (LocalDate ele:lcd)
        {
            if (iter.equals(ele.toString())) continue;
            iter = ele.toString();
            String tmp = DateTool.convertMonthShort(""+ele.getMonthValue());
            if(!re.containsKey(tmp))
            {
                ArrayList<Spending> tmp2 = new ArrayList<>();
                for(Spending ele2:map.get(ele)) tmp2.add(ele2);
                re.put(tmp,tmp2);
            }
            else {
                for(Spending ele2:map.get(ele)) re.get(tmp).add(ele2);
            }
        }
        return re;
    }

    static public HashMap<String, ArrayList<Spending>> mergeByDay(List<Spending> finances)
    {
        Log.d("input", finances.toString());
        HashMap<String,ArrayList<Spending>> re = new HashMap<>();
        ArrayList<LocalDate> lcd = new ArrayList<>();
        Map<LocalDate,ArrayList<Spending>> map = new HashMap<>();

        for (Spending ele:finances)
        {
            LocalDate tmp = LocalDate.parse(ele.getDate(), DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            lcd.add(tmp);
            if (!map.containsKey(tmp)){
                ArrayList tmpArr = new ArrayList();
                tmpArr.add(ele);
                map.put(tmp,tmpArr);
            }
            else map.get(tmp).add(ele);
        }
        Collections.sort(lcd);
        String iter = "";
        for (LocalDate ele:lcd)
        {
            if (iter.equals(ele.toString())) continue;
            iter = ele.toString();
            String tmp = ""+ele.getDayOfMonth();

            if(!re.containsKey(tmp))
            {
                ArrayList<Spending> tmp2 = new ArrayList<>();
                for(Spending ele2:map.get(ele)) tmp2.add(ele2);
                re.put(tmp,tmp2);
            }
            else {
                for(Spending ele2:map.get(ele)) re.get(tmp).add(ele2);
            }
        }

        return re;
    }
    static public Long computeAmount(List<Spending> finances)
    {
        Long s = 0l;
        for (Spending ele:finances) s += ele.getAmount();
        return s;
    }

    static public HashMap<Long,Long> mergeByCategory(List<Spending> finances)
    {
        HashMap<Long, Long> hm = new HashMap<>();
        long id;
        for (Spending ele:finances)
        {
            id = ele.getCategoryId();
            if (!hm.containsKey(id)){
                hm.put(id,ele.getAmount());
            }else {
                Long tmp = hm.get(id) + ele.getAmount();
                hm.replace(id,tmp);
            }
        }
        return hm;
    }

    static public HashMap<String, ArrayList<Spending>> mergeByYear(List<Spending> finances)
    {
        HashMap<String,ArrayList<Spending>> re = new HashMap<>();
        ArrayList<LocalDate> lcd = new ArrayList<>();
        Map<LocalDate,ArrayList<Spending>> map = new HashMap<>();

        for (Spending ele:finances)
        {
            LocalDate tmp = LocalDate.parse(ele.getDate(), DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            lcd.add(tmp);
            if (!map.containsKey(tmp)){
                ArrayList tmpArr = new ArrayList();
                tmpArr.add(ele);
                map.put(tmp,tmpArr);
            }
            else map.get(tmp).add(ele);
        }
        Collections.sort(lcd);
        String iter = "";
        for (LocalDate ele:lcd)
        {
            if (iter.equals(ele.toString())) continue;
            iter = ele.toString();
            String tmp = ""+ele.getYear();

            if(!re.containsKey(tmp))
            {
                ArrayList<Spending> tmp2 = new ArrayList<>();
                for(Spending ele2:map.get(ele)) tmp2.add(ele2);
                re.put(tmp,tmp2);
            }
            else {
                for(Spending ele2:map.get(ele)) re.get(tmp).add(ele2);
            }
        }

        return re;
    }

    static public Integer[] getMaxYearInSeq(List<Spending> finances)
    {
        ArrayList<LocalDate> lcd = new ArrayList<>();
        Integer[] minmax = new Integer[2];
        int max = 0,min = 3000;
        for (Spending ele:finances)
        {
            LocalDate tmp = LocalDate.parse(ele.getDate(), DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            lcd.add(tmp);
        }

        for (LocalDate ele:lcd)
        {
            int tmp = ele.getYear();
            min = min > tmp? tmp:min;
            max = max < tmp? tmp:max;
        }
        minmax[0] = min;
        minmax[1] = max;
        return minmax;
    }
}
