package com.high_con.grad.dao;

import com.high_con.grad.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminDao {

    public List<User> getAllUser();

    public void InsertCourse();

}
