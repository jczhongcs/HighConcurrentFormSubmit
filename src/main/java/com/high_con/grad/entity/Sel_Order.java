package com.high_con.grad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sel_order")
public class Sel_Order implements Serializable {

    @Id
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
