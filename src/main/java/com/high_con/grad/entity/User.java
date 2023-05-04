package com.high_con.grad.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="t_user")
public class User implements Serializable{
    /*@TableId(value = "id",type = IdType.INPUT)*/
    @Id
    private Long id;

    private String nickname;

    private String password;

    private String salt;


    private Date registerDate;



    private String idcard;

    private String sex;

    private String phone;

    private String email;

    private String degree;//学历

    private String grade;

    private String nation; //民族

    private String isnormal; //是否师范生

    private String politics; //政治面貌

    private String ethnic; //籍贯

    private String address;

    private Integer role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String ism1;
    private String m1Name;

    private String m1Relate;

    private String m1Phone;

    private String ism2;
    private String m2Name;

    private String m2Relate;

    private String m2Phone;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }



    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getIsnormal() {
        return isnormal;
    }

    public void setIsnormal(String isnormal) {
        this.isnormal = isnormal;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getM1Name() {
        return m1Name;
    }

    public void setM1Name(String m1Name) {
        this.m1Name = m1Name;
    }

    public String getM1Relate() {
        return m1Relate;
    }

    public void setM1Relate(String m1Relate) {
        this.m1Relate = m1Relate;
    }

    public String getM1Phone() {
        return m1Phone;
    }

    public void setM1Phone(String m1Phone) {
        this.m1Phone = m1Phone;
    }

    public String getM2Name() {
        return m2Name;
    }

    public void setM2Name(String m2Name) {
        this.m2Name = m2Name;
    }

    public String getM2Relate() {
        return m2Relate;
    }

    public void setM2Relate(String m2Relate) {
        this.m2Relate = m2Relate;
    }

    public String getM2Phone() {
        return m2Phone;
    }

    public void setM2Phone(String m2Phone) {
        this.m2Phone = m2Phone;
    }


    public String getIsm1() {
        return ism1;
    }

    public void setIsm1(String ism1) {
        this.ism1 = ism1;
    }

    public String getIsm2() {
        return ism2;
    }

    public void setIsm2(String ism2) {
        this.ism2 = ism2;
    }
}
