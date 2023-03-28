package com.high_con.grad.util;

import java.util.UUID;

public class Session_Util {
    public static String UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
