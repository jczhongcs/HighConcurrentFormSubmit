package com.high_con.grad.redis;

public class OrderKey extends Base_Pr{
    public OrderKey(String pref) {
        super(pref);
    }

    public static OrderKey getKillOrderByUidGid = new OrderKey("koug");
}
