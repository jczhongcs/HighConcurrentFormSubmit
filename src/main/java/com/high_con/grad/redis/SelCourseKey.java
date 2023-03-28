package com.high_con.grad.redis;

public class SelCourseKey extends Base_Pr{
    public SelCourseKey(String pref) {
        super(pref);
    }

    public static SelCourseKey getSelCourseByUidGid = new SelCourseKey("koug");
}
