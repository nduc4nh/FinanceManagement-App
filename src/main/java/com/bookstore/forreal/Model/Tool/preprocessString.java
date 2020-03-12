package com.bookstore.forreal.Model.Tool;

public  class preprocessString
{
    public static String doString(String str) {return str.strip().toLowerCase();}
    public static String[] toArray(String str,String separated_letter) {return str.strip().split(separated_letter);}
}