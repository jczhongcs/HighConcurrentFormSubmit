package com.high_con.grad.util;

import org.springframework.util.DigestUtils;

public class MD5_Util {

    private static final String salt = "5zi7ng9";

    public static String md5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }



    public static String inputPassFormPass(String inputstr){
        String str = "" + salt.charAt(1) + inputstr + salt.charAt(2)+salt.charAt(5)+salt.charAt(0);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt){
        String str = "" + salt.charAt(1) + formPass + salt.charAt(2)+salt.charAt(5)+salt.charAt(0);
        return md5(str);
    }

    public static String inputPassToDbPass(String input,String saltDB){
        String formPass = inputPassFormPass(input);
        String dbPass = formPassToDbPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args){
       //System.out.println(inputPassFormPass("123456")); //dad120e31d82e436081cc7e93a0d9d98
        //System.out.println(formPassToDbPass(inputPassFormPass("123456"),"5zi7ng9"));
        //System.out.println(inputPassToDbPass("123456","5zi7ng9"));
    }

}
