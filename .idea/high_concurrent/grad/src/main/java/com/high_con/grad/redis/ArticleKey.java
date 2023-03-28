package com.high_con.grad.redis;

public class ArticleKey extends Base_Pr{

    public ArticleKey(int expire,String pre) {
        super(expire,pre);
    }

    public static ArticleKey getGL = new ArticleKey(45,"gl");

    public static ArticleKey getGT = new ArticleKey(45,"gt");

    public static ArticleKey getKillStock = new ArticleKey(0,"gs");
}
