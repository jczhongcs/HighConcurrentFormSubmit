package com.high_con.grad.dao;

import com.high_con.grad.entity.Feedback;
import com.high_con.grad.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface FeedbackDao {



    @Select("select * from feedback where id = #{id}")
    public Feedback getById(@Param("id")long id );


    @Delete("DELETE FROM feedback WHERE id=#{id}" )
    int delete(Long id);

    @Select("select * from feedback WHERE user_id = #{userId}")
    public List<Feedback> listFeedbackVoByUserId(Long userId);

    @Select("select * from feedback")
    List<Feedback> listFeedback();
}
