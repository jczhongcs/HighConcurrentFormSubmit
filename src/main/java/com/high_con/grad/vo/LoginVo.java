package com.high_con.grad.vo;

import com.high_con.grad.valid.IsPhone;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class LoginVo {
    @NotNull
    @IsPhone
    private String phone;
    @NotNull
    @Length(min=6)
    private String pwd;

    @NotNull
    private int role;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +",role="+role+'\''+
                '}';
    }
}
