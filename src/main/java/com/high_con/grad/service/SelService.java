package com.high_con.grad.service;

import com.high_con.grad.entity.*;
import com.high_con.grad.redis.KillKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.SelKey;
import com.high_con.grad.vo.CourseVo;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SelService {

    @Autowired
    CourseService courseService;
    @Autowired
    OrderService orderService;

    @Autowired
    C_OrderService c_orderService;

    @Autowired
    RedisService redisService;


    @Transactional
    public C_Order sel(User user, CourseVo courseVo) {

        boolean success = courseService.reduceRemain(courseVo);
        if (success) {
            return c_orderService.createOrder(user,courseVo);
        } else {
            setCourseOver(courseVo.getId());
            return null;
        }
        //System.out.println("reduction is OK");
    }



    public long getSelResult(Long userid, long courseId) {

        Sel_Order sel_order = c_orderService.getSelCourseByUserIdCoursesId(userid,courseId);
        if (sel_order != null) {
            System.out.println(sel_order.getC_orderId());
            return sel_order.getC_orderId();
        } else {
            boolean isOver = getCourseOver(courseId);
            if (isOver) {
                return -1;
            }
            else {
                return 0;
            }
        }

    }

    private boolean getCourseOver(long courseId) {
        return redisService.exist(SelKey.isCourseSelectedout,""+courseId);

    }

    private void setCourseOver(Long courseId) {
        redisService.set(SelKey.isCourseSelectedout,""+courseId,true);
    }


}
