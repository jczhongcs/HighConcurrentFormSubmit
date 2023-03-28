package com.high_con.grad.dao;

import com.high_con.grad.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {



    @Select("select * from t_user where id = #{id}")
    public User getById(@Param("id")long id );

    @Insert("Insert into t_user(id,nickname,password,salt,register_date,login_count,phone,role)" +
            "values(#{id},#{nickname},#{password},#{salt},#{register},#{logincount},#{phone},#{role})")
    int insertUser(long id, String nickname, String password, String salt, Date register,int logincount,String phone,int role);

    @Update("update t_user set password = #{password} where id = #{id}")
    void update(User toUpd);

    @Update("update t_user set password = #{password}, nickname=#{nickname}, phone = #{phone}, role = #{role} where id = #{id}")
    int updateUser(Long id,String password,String nickname,String phone,int role);


    @Delete("DELETE FROM t_user WHERE id=#{id}" )
    int delete(Long id);

    @Select("select * from t_user")
    public List<User> listUsersVo();
}
