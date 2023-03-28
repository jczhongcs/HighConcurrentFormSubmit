package com.high_con.grad.redis;

public class C_OrderKey extends Base_Pr{
    public C_OrderKey(String pref) {
        super(pref);
    }

    public static C_OrderKey getSelCourseByUserIdCourseId = new C_OrderKey("scuc");
}
