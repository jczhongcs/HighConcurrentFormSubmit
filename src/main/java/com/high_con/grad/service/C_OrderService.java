package com.high_con.grad.service;

import com.high_con.grad.dao.CourseDao;
import com.high_con.grad.dao.OrderDao;
import com.high_con.grad.entity.*;
import com.high_con.grad.redis.C_OrderKey;
import com.high_con.grad.redis.OrderKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.vo.CourseVo;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class C_OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    @Autowired
    CourseDao courseDao;




    public Sel_Order getSelCourseByUserIdCoursesId(long userId, long coursesId) {
        return redisService.get(C_OrderKey.getSelCourseByUserIdCourseId,""+userId+"_"+coursesId,Sel_Order.class);
    }

    public Order getCourseById(long orderId) {
        return courseDao.getCourseById(orderId);

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
