package com.high_con.grad.redis;

public abstract class Base_Pr implements KeyPre{


    private  int expire;

    private String pref;

    public Base_Pr(String pre){
        this(0,pre);
    }


    public Base_Pr(int expire,String pref){
        this.expire=expire;
        this.pref=pref;
    }

    @Override
    public int expire_time() {
        return expire;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + pref;
    }
}
