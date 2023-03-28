package com.high_con.grad.rab_m;

import com.high_con.grad.entity.User;

public class SelMsg {
    private User user;
    private long courseId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
