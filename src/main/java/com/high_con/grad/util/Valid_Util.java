package com.high_con.grad.util;

import com.mysql.cj.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valid_Util {

    private static final Pattern phone_patt = Pattern.compile("1\\d{10}");

    public static boolean isPhoneNum(String str){
        if(StringUtils.isEmptyOrWhitespaceOnly(str)){
            return false;
        }
        Matcher m = phone_patt.matcher(str);

        return m.matches();
    }

    public static void main(String[] args){
        System.out.println(isPhoneNum("15367855832"));

    }

}
