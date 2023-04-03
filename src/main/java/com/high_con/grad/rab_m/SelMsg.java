package com.high_con.grad.rab_m;

import com.high_con.grad.entity.User;

public class SelMsg {
    private User user;
    private long courseId;

    private String userPhone;

    private String userGrade;


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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }
}
