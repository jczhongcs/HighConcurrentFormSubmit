package com.high_con.grad.service;

import com.high_con.grad.controller.SelController;
import com.high_con.grad.dao.CourseDao;
import com.high_con.grad.dao.OrderDao;
import com.high_con.grad.entity.*;
import com.high_con.grad.mapper.CourseInfoMapper;
import com.high_con.grad.mapper.CourseMapper;
import com.high_con.grad.mapper.SelOrderMapper;
import com.high_con.grad.redis.C_OrderKey;
import com.high_con.grad.redis.CourseKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class C_OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    @Autowired
    CourseDao courseDao;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SelOrderMapper selOrderMapper;
    @Autowired
    CourseInfoMapper courseInfoMapper;



    public List<Sel_Order> getSelOrder(){
        List<Sel_Order> list =redisService.gets(C_OrderKey.getSelCourseByUserIdCourseId,Sel_Order.class);
        System.out.println(list.get(0).getC_orderId());
        return list;
    }


    public Sel_Order getSelCourseByUserIdCoursesId(long userId, long coursesId) {
        return redisService.get(C_OrderKey.getSelCourseByUserIdCourseId,""+userId+"_"+coursesId,Sel_Order.class);

    }

    public Sel_Order getSelCourseByUserIdCoursesId(Sel_Order sel_order) {
        return courseDao.getSelCourseByCourseId(sel_order.getUserId(),sel_order.getCourseId());

    }

    public C_Order getCourseById(Long id) {
        return courseDao.getCourseById(id);

    }


    @Transactional
    public int deleteOrder(Long id) {
        C_Order c_order= getCourseById(id);
        //System.out.println("?");
        System.out.println(c_order);
        courseInfoMapper.deleteByPrimaryKey(c_order);

        Sel_Order sel_order = getSelCourseByUserIdCoursesId(c_order.getUserId(),c_order.getCourseId());

        sel_order= getSelCourseByUserIdCoursesId(sel_order);
        selOrderMapper.deleteByPrimaryKey(sel_order);

        SelCourse selCourse = new SelCourse();
        selCourse.setCourseId(c_order.getCourseId());
        courseDao.addRemain(selCourse);
       long incre = redisService.incre(CourseKey.getCourseStock, "" + c_order.getCourseId());

       redisService.del(C_OrderKey.getSelCourseByUserIdCourseId,""+c_order.getUserId()+"_"+c_order.getCourseId());




        return 1;

    }


    @Transactional
    public C_Order createOrder(User user, CourseVo courseVo) {


        C_Order c_order = new C_Order();
        c_order.setCourseId(courseVo.getId());
        c_order.setUserId(user.getId());
        c_order.setCourseName(courseVo.getCourseName());
        //c_order.setCourseDetail(courseVo.getCourseDetail());
        c_order.setCourseStartDate(courseVo.getStartDate());
        c_order.setCourseEndDate(courseVo.getEndDate());
        c_order.setUserGrade(courseVo.getUserGrade());
        c_order.setUserPhone(courseVo.getUserPhone());
        c_order.setChooseReason(courseVo.getUserChooseReason());
        courseDao.insert(c_order);

        Sel_Order sel_order = new Sel_Order();
        sel_order.setCourseId(courseVo.getId());
        sel_order.setC_orderId(c_order.getId());
        sel_order.setUserId(user.getId());
        courseDao.insertSelOrder(sel_order);
        //System.out.println("set selorder is ok");

        redisService.set(C_OrderKey.getSelCourseByUserIdCourseId,""+user.getId()+"_"+courseVo.getId(),sel_order);

        return c_order;

    }


}
