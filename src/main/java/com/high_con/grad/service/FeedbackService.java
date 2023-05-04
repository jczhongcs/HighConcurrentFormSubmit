package com.high_con.grad.service;

import com.high_con.grad.dao.FeedbackDao;
import com.high_con.grad.entity.Feedback;
import com.high_con.grad.mapper.FeedbackMapper;
import com.high_con.grad.redis.FeedbackKey;
import com.high_con.grad.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    FeedbackDao feedbackDao;

    public Feedback getByfeedbackid(Long id){

        return feedbackDao.getById(id);
    }

    public void deleteFeedback(Long id){
        Feedback feedback = feedbackDao.getById(id);
        //String key = FeedbackKey.feedbackpref.getPrefix()+feedback.getUserId()+feedback.getUuid();
        String key = feedback.getUserId()+feedback.getUuid();
        redisService.del(FeedbackKey.feedbackpref,key);
        feedbackMapper.delete(feedback);
    }

    public List<Feedback> getFeedListByUserId(Long userId){

        return feedbackDao.listFeedbackVoByUserId(userId);
    }

    public List<Feedback> getFeedList(){
        return  feedbackDao.listFeedback();
    }


    public boolean isSameFromSameUser(Feedback feedback) {
        String subfeed = String.valueOf(feedback.getUserId());
        List<Feedback> feedbackList = redisService.gets(FeedbackKey.feedbackpref,subfeed,Feedback.class);
        for(Feedback feed:feedbackList){
            if(isSameFeed(feedback,feed)) return true;
        }
        return false;
    }

    private boolean isSameFeed(Feedback newfeedback,Feedback oldfeedback) {
        //System.out.println(newfeedback);
        //System.out.println(oldfeedback);
        if(oldfeedback.getFeedBackContent().equals(newfeedback.getFeedBackContent())&&oldfeedback.getFeedBackType().equals(newfeedback.getFeedBackType())){
            if(oldfeedback.getFeedBackDescribe().equals(newfeedback.getFeedBackDescribe())){
                if(oldfeedback.getSelSat().equals(newfeedback.getSelSat())&&oldfeedback.getCourseSat().equals(newfeedback.getCourseSat())){
                    if(oldfeedback.getTotalSat().equals(newfeedback.getTotalSat())){
                        //System.out.println("same feed");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
