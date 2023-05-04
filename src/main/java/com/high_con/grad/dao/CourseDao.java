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

    @Select("select * from course_info")
    List<CourseVo> listAllSelcourse();

    @Insert("insert into sel_order(user_id,course_id,c_order_id)values(#{userId},#{courseId},#{c_orderId})")
    public int insertSelOrder(Sel_Order sel_order);

    @Update("update sel_course set course_remain = course_remain - 1 where course_id = #{courseId} and course_remain > 0")
    public int reduceRemain(SelCourse course);

    @Update("update sel_course set course_remain = course_remain + 1 where course_id = #{courseId} and course_remain > 0")
    public int addRemain(SelCourse course);


    @Select("select * from t_course")
    public List<TCourse> listCourse();

    @Select("select * from course_info where id = #{id}")
    C_Order getCourseById(long id);


    @Insert("insert into course_info(user_id,course_id,course_name,course_start_date,course_end_date,user_grade,user_phone,choose_reason)values(" +
            "#{userId},#{courseId},#{courseName},#{courseStartDate},#{courseEndDate},#{userGrade},#{userPhone},#{chooseReason})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    void insert(C_Order c_order);

    @Select("select * from course_info where user_id = #{userId} ")
    List<CourseVo> listCourseVoByUserId(Long userId);

    @Select("select * from sel_order where user_id=#{userId} and course_id=#{courseId}")
    Sel_Order getSelCourseByCourseId(Long userId,Long courseId);

    @Select("select * from t_course where id=#{id}")
    TCourse getCourse(Long id);

    @Select("select * from sel_course where course_id=#{courseId}")
    SelCourse getSelCourse(long courseId);
}
