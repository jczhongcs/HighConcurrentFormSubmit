package com.high_con.grad.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "feedback")
public class Feedback implements Serializable {
    @Id
    private Long id;

    private Long userId;

    private String userNickname;

    private String userPhone;

    private String userEmail;

    private String userGrade;

    private String feedBackType;

    private String feedBackDescribe;

    private String feedBackContent;

    private String courseSat;

    private String selSat;

    private String totalSat;

    private String uuid;


}
