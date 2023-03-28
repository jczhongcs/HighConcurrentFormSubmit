package com.high_con.grad.redis;

public class KillKey extends Base_Pr{
    public KillKey(String pref) {
        super(pref);
    }

    public static KillKey isGoodsSoldout = new KillKey("sold_out");

}
