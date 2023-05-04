package com.high_con.grad.mapper;



import com.high_con.grad.SearchVo.CourseSearchVo;
import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.entity.TCourse;
import tk.mybatis.mapper.common.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("CourseMapper")
public interface CourseMapper extends Mapper<TCourse>{

   List<TCourse> getCourseListByPage(@Param("courseSearchVo") CourseSearchVo courseSearchVo);

}
