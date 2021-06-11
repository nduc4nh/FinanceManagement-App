package com.example.financemanagementapplication.tool;

import java.util.Stack;

public class Converter {
    static public String rawToReadable(Long l)
    {
        Stack<Character> stack = new Stack<Character>();
        String a1 = String.valueOf(l);
        char[] x = a1.toCharArray();
        String re = "";
        int count = 0;
        for (int i = a1.length() - 1; i >= 0;--i)
        {
            stack.add(x[i]);
            count += 1;
            if (count == 3){
                count = 0;
                if (i == 1 && x[0] == '-') continue;
                if (i != 0) stack.add(',');
            }
        }
        while (!stack.empty())
        {
            re += stack.pop();
        }
        re += " vnd";
        return  re;
    }
    static public Long readableToRaw(String s)
    {
        char[] x = s.toCharArray();
        int iter = 0;
        String re = "";
        while (iter < s.length() && x[iter] != ' ')
        {
            if (iter < s.length() && x[iter] != ',') re += x[iter];
            iter += 1;
        }
        return Long.parseLong(re);
    }

}
