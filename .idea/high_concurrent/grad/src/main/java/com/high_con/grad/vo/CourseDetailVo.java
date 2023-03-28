package com.high_con.grad.vo;


import com.high_con.grad.entity.TCourse;
import com.high_con.grad.entity.User;

public class CourseDetailVo {

    private CourseVo courseVo;
    private TCourse course;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private int Status = 0;
    private int remain = 0;

    private User user;

    public CourseVo getCourseVo() {
        return courseVo;
    }

    public void setCourseVo(CourseVo courseVo) {
        this.courseVo = courseVo;
    }

    public TCourse getCourse() {
        return course;
    }

    public void setCourse(TCourse course) {
        this.course = course;
    }
}
