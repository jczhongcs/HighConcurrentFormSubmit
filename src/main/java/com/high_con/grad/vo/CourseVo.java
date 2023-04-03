package com.high_con.grad.vo;

import com.high_con.grad.entity.TCourse;

import java.util.Date;

public class CourseVo extends TCourse {

    public Date getStartTime() {
        return startTime;
    }



    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    Date startTime;

    Date endTime;


    private String userPhone;

    private String userGrade;


    public int getCourseRemain() {
        return courseRemain;
    }

    public void setCourseRemain(int courseRemain) {
        this.courseRemain = courseRemain;
    }

    private int courseRemain;


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
