package com.high_con.grad.rab_m;

import com.high_con.grad.entity.Feedback;
import com.high_con.grad.entity.User;

public class FeedMsg {
    private Feedback feedback;

    private User user;

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
