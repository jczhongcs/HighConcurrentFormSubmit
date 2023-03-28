package com.high_con.grad.entity;

public class Sel_Order {
    private Long id;
    private Long userId;

    public Long getC_orderId() {
        return c_orderId;
    }

    public void setC_orderId(Long c_orderId) {
        this.c_orderId = c_orderId;
    }

    private Long  c_orderId;
    private Long courseId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
