package com.high_con.grad.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.entity.User;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserMapper extends Mapper<User> {


 List<User> getUserListByPage(@Param("userSearchVo") UserSearchVo userSearchVo);



}
