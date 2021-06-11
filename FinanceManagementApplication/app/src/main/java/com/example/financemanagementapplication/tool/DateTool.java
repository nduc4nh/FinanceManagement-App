package com.example.financemanagementapplication.tool;

import android.util.Log;

import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTool {

    static public String today()
    {
        LocalDate re = LocalDate.now();
        return re.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

   static public String yesterday(String current)
    {
        //LocalDate re = parse(current);
        LocalDate re = LocalDate.parse(current, DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        re = re.minusDays(1);
        return re.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        //return ""+re.getDayOfMonth() + "/" + re.getMonthValue() +"/" +re.getYear();
    }

    static public String tomorrow(String current){
        //LocalDate re = parse(current);
        LocalDate re = LocalDate.parse(current, DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        re = re.plusDays(1);
        return re.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        //return ""+re.getDayOfMonth() + "/" + re.getMonthValue() +"/" +re.getYear();
    }
    @Deprecated
    static public LocalDate parse(String date)
    {
        int d,m,y;
        String[] ele = date.split("/");
        d = Integer.parseInt(ele[0]);
        m = Integer.parseInt(ele[1]);
        y = Integer.parseInt(ele[2]);

        LocalDate re = LocalDate.of(y, m, d);
        return re;
    }

    static public LocalDate parseString(String date)
    {
        return LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    static public Integer getMonth(String date)
    {
        LocalDate re = LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        return re.getMonthValue();
    }

    static public String convertMonth(String raw)
    {
        if (raw.equals("1")) return "January";
        if (raw.equals("2")) return "February";
        if (raw.equals("3")) return "March";
        if (raw.equals("4")) return "April";
        if (raw.equals("5")) return "May";
        if (raw.equals("6")) return "June";
        if (raw.equals("7")) return "July";
        if (raw.equals("8")) return "August";
        if (raw.equals("9")) return "September";
        if (raw.equals("10")) return "October";
        if (raw.equals("11")) return "November";
        if (raw.equals("12")) return "December";
        return "";
    }
    static public String convertMonthShort(String raw)
    {
        if (raw.equals("1")) return "Jan";
        if (raw.equals("2")) return "Feb";
        if (raw.equals("3")) return "Mar";
        if (raw.equals("4")) return "Apr";
        if (raw.equals("5")) return "May";
        if (raw.equals("6")) return "Jun";
        if (raw.equals("7")) return "Jul";
        if (raw.equals("8")) return "Aug";
        if (raw.equals("9")) return "Sep";
        if (raw.equals("10")) return "Oct";
        if (raw.equals("11")) return "Nov";
        if (raw.equals("12")) return "Dec";
        return "";
    }
    static public String inverseMonth(String readable)
    {
        if (readable.equals("January")) return "1";
        if (readable.equals("February")) return "2";
        if (readable.equals("March")) return "3";
        if (readable.equals("April")) return "4";
        if (readable.equals("May")) return "5";
        if (readable.equals("June")) return "6";
        if (readable.equals("July")) return "7";
        if (readable.equals("August")) return "8";
        if (readable.equals("September")) return "9";
        if (readable.equals("October")) return "10";
        if (readable.equals("November")) return "11";
        if (readable.equals("December")) return "12";
        return "";
    }
    static public String toSQLReadable(String mode, String value)
    {
        if (mode.equals("d"))
        {
            return "% "+value+", %";
        }
        else if (mode.equals("m"))
        {
            return convertMonth(value)+" %, %";
        }
        else
        {
            return "% %, "+value;
        }
    }
    static public ArrayList<String> getMonthsInYear()
    {
        ArrayList<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
        return months;
    }
    static public ArrayList<String> getDaysInMonth(String month)
    {
        ArrayList<String> tmp1 = new ArrayList<>();
        ArrayList<String> tmp2 = new ArrayList<>();
        ArrayList<String> tmp3 = new ArrayList<>();
        for (int i = 1; i <= 31; ++i)
        {
            if (i <= 28)
            {
                tmp1.add(String.valueOf(i));
                tmp2.add(String.valueOf(i));
                tmp3.add(String.valueOf(i));
            }
            else  if (i <= 30)
            {
                tmp1.add(String.valueOf(i));
                tmp3.add(String.valueOf(i));
            }
            else tmp3.add(String.valueOf(i));
        }

        if (month.equals("1")||
                month.equals("3")||
                month.equals("5")||
                month.equals("7")||
                month.equals("8")||
                month.equals("10")||
                month.equals("12")||
                month.equals(convertMonth("1"))||
                month.equals(convertMonth("3"))||
                month.equals(convertMonth("5"))||
                month.equals(convertMonth("7"))||
                month.equals(convertMonth("8"))||
                month.equals(convertMonth("10"))||
                month.equals(convertMonth("12"))
        ) return tmp3;

        if (month.equals("4")||
                month.equals("6")||
                month.equals("9")||
                month.equals("11")||
                month.equals(convertMonth("4"))||
                month.equals(convertMonth("6"))||
                month.equals(convertMonth("9"))||
                month.equals(convertMonth("11"))
        ) return tmp1;

        if (month.equals("2")||month.equals(convertMonth("2"))) return tmp2;
        return null;
    }


}
