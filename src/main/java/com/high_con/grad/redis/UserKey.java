package com.high_con.grad.redis;

public class UserKey extends Base_Pr{

    public static final int Token_Expire = 24*3600;

    private UserKey(int expire,String pre){
        super(expire,pre);
    }

    public static UserKey tokenid = new UserKey(Token_Expire,"tk");

    public static UserKey getid = new UserKey(0,"id");
    //public static UserKey getByName = new UserKey("name");

}
