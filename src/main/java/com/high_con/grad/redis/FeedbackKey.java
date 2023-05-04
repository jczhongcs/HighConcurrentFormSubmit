package com.high_con.grad.redis;

public class FeedbackKey extends Base_Pr{

    public FeedbackKey(String pref) {
        super(pref);
    }

    public static FeedbackKey feedbackpref = new FeedbackKey("fdbu");
}
