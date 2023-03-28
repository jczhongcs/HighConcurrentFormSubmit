package com.high_con.grad.entity;

import java.util.Date;

public class SelCourse {

    private Long id;
    private Long courseId;
    private Integer courseRemain;
    private Date startTime;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseRemain() {
        return courseRemain;
    }

    public void setCourseRemain(Integer courseRemain) {
        this.courseRemain = courseRemain;
    }

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
}
