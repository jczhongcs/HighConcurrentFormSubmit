package com.high_con.grad.service;

import com.github.pagehelper.PageHelper;
import com.high_con.grad.dao.CourseDao;
import com.high_con.grad.entity.*;
import com.high_con.grad.mapper.CourseMapper;
import com.high_con.grad.mapper.SelCourseMapper;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;

@Autowired
    SelCourseMapper selCourseMapper;

    @Autowired
    RedisService redisService;
    @Autowired
    CourseDao courseDao;





    // @Transactional
    public boolean reduceRemain(CourseVo courseVo) {
        SelCourse selCourse = new SelCourse();
        selCourse.setCourseId(courseVo.getId());
        //System.out.println("Set good is ok");
        int ret = courseDao.reduceRemain(selCourse);
        return ret > 0 ;
    }

    public List<CourseVo> listCourseVo(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return courseDao.listCourseVo();
    }

    public List<CourseVo> listCourseByUserId(Long userId){
        return courseDao.listCourseVoByUserId(userId);
    }

    public List<CourseVo> listCourseByAdmin(){
        return courseDao.listAllSelcourse();
    }
    public List<CourseVo> listCourseVo() {

        return courseDao.listCourseVo();
    }

    public List<TCourse> listCourse() {
        return courseDao.listCourse();
    }

    public CourseVo getCoursesVoByCoursesId(long coursesId) {
        return courseDao.getCourseVoByCourseId(coursesId);
    }

    public TCourse getCourse(long courseId){
        return courseDao.getCourse(courseId);
    }

    public SelCourse getSelCourse(long courseId) {return courseDao.getSelCourse(courseId);}


    public void sel(User user, CourseVo courseVo) {
    }

    public void deleteCourse(Long id) {
        TCourse course = getCourse(id);
        SelCourse selCourse = getSelCourse(id);
        courseMapper.delete(course);
        selCourseMapper.delete(selCourse);


    }
}
