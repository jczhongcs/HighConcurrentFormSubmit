package com.high_con.grad.rab_m;

import com.high_con.grad.dao.UserDao;
import com.high_con.grad.entity.*;
import com.high_con.grad.mapper.FeedbackMapper;
import com.high_con.grad.mapper.UserMapper;
import com.high_con.grad.redis.FeedbackKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.service.*;
import com.high_con.grad.vo.CourseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Receiver {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;


    @Autowired
    CourseService courseService;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    SelService selService;

    @Autowired
    C_OrderService c_orderService;

    @Autowired
    UserDao userDao;

    private static Logger l = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = RaConfig.Kill_Queue)
    public void q_rec(String q_msg){
         //l.info("receive msg:"+q_msg);
        KillMsg killMsg = RedisService.stringToBean(q_msg,KillMsg.class);
        User user = killMsg.getUser();
        long goodsId = killMsg.getGoodsId();
        //判断余量
        //判断
        //真正减少
    }


    @RabbitListener(queues = RaConfig.Feedback_Queue)
    public void rec_feed(String q_msg){
        //l.info("receive msg:"+q_msg);
       FeedMsg feedMsg = RedisService.stringToBean(q_msg,FeedMsg.class);
        Feedback feedback = feedMsg.getFeedback();
        if(feedbackService.isSameFromSameUser(feedback)){
            //System.out.println("?");
            return;
        }
        feedbackMapper.insert(feedback);
        //System.out.println("do insert feed");
        redisService.set(FeedbackKey.feedbackpref,""+feedback.getUserId()+feedback.getUuid(),feedback);
    }



    @RabbitListener(queues = RaConfig.User_Update_Queue)    //
    public void rec_user(String q_msg){
        //l.info("receive msg:"+q_msg);

        UserMsg userMsg = RedisService.stringToBean(q_msg,UserMsg.class);
        User user = userMsg.getUser1();
        User user1 = userMsg.getUser2();
        if(user1!=null) {
            if (userService.isSameUserInfo(user, user1)) {
               // System.out.println("issame");
                return ;
            }
        }
        //System.out.println(user);
        //System.out.println("after");
        int result = userMapper.updateByPrimaryKey(user);
        //System.out.println("isupdate");
    }


    @RabbitListener(queues = RaConfig.Sel_Queue)
    public void rec(String q_msg){
        //l.info("receive msg:"+q_msg);
       /* KillMsg killMsg = RedisService.stringToBean(q_msg,KillMsg.class);*/
        SelMsg selMsg = RedisService.stringToBean(q_msg,SelMsg.class);
        User user = selMsg.getUser();
        String userPhone = user.getPhone();
        String userGrade = user.getGrade();
        String userChooseReason = selMsg.getUserChooseReason();




        long courseId = selMsg.getCourseId();
        //判空

        CourseVo courseVo = courseService.getCoursesVoByCoursesId(courseId);
        courseVo.setUserGrade(userGrade);
        courseVo.setUserPhone(userPhone);
        courseVo.setUserChooseReason(userChooseReason);
        int remain = courseVo.getCourseRemain();
        if(remain<=0){
            return ;
        }
        //判断是否有建立
        Sel_Order sel_order = c_orderService.getSelCourseByUserIdCoursesId(user.getId(), courseId);
        if(sel_order != null){
            return ;
        }
        //最后再进行真正选课操作
        selService.sel(user,courseVo);
    }


   /* @RabbitListener(queues = RaConfig.Queue_Name)
    public void q_rec(String q_msg){
       // l.info("receive msg:"+q_msg);
    }

    @RabbitListener(queues = RaConfig.TOPP_Queue1)
    public void q_rec_t1(String q_msg){
         l.info("receive topp1:"+q_msg);
    }

    @RabbitListener(queues = RaConfig.TOPP_Queue2)
    public void q_rec_t2(String q_msg){
        l.info("receive topp2:"+q_msg);
    }

    @RabbitListener(queues = RaConfig.HEAD_Queue)
    public void q_rec_h(byte[] q_msg){
        l.info("receive header:"+new String(q_msg));
    }
*/
}
