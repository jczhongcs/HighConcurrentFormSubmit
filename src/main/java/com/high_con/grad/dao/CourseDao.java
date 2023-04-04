package com.high_con.grad.dao;


import com.high_con.grad.entity.*;
import com.high_con.grad.vo.CourseVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseDao {

    @Select("select c.*,sc.course_remain, sc.start_time, sc.end_time from sel_course sc left join t_course c on sc.course_id = c.id")
    public List<CourseVo> listCourseVo();

    @Select("select c.*,sc.course_remain, sc.start_time, sc.end_time from sel_course sc left join t_course c on sc.course_id = c.id where c.id = #{courseId} ")
    public CourseVo getCourseVoByCourseId(@Param("courseId")long courseId);

    @Update("update sel_course set course_remain = course_remain - 1 where course_id = #{courseId} and course_remain > 0")
    public int reduceStock(Kill_Goods goods);

    @Insert("insert into sel_order(user_id,course_id,c_order_id)values(#{userId},#{courseId},#{c_orderId})")
    public int insertSelOrder(Sel_Order sel_order);

    @Update("update sel_course set course_remain = course_remain - 1 where course_id = #{courseId} and course_remain > 0")
    public int reduceRemain(SelCourse course);
    @Select("select * from t_course")
    public List<TCourse> listCourse();

    Order getCourseById(long orderId);

    @Insert("insert into course_info(user_id,course_id,course_name,course_start_date,course_end_date,user_grade,user_phone,choose_reason)values(" +
            "#{userId},#{courseId},#{courseName},#{courseStartDate},#{courseEndDate},#{userGrade},#{userPhone},#{chooseReason})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    void insert(C_Order c_order);
}
