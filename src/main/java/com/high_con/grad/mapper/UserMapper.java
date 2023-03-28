package com.high_con.grad.mapper;



import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("UserMapper")
public interface UserMapper {


 List<User> getUserListByPage(@Param("userSearchVo") UserSearchVo userSearchVo);



}
