package com.high_con.grad.service;

import com.high_con.grad.dao.AdminDao;
import com.high_con.grad.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;


    public List<User> getAllUser(){


        return null;
    }

    public void InsertCourse(){

    }


}
