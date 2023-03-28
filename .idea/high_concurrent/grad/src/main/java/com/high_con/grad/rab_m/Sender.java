package com.high_con.grad.rab_m;

import com.high_con.grad.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    AmqpTemplate amqpTemplate;

    private static Logger l = LoggerFactory.getLogger(Sender.class);

    public void sendKillMsg(KillMsg killMsg) {
        String q_msg = RedisService.beanToString(killMsg);
        //l.info("send msg:"+q_msg);
        amqpTemplate.convertAndSend(RaConfig.Kill_Queue,q_msg);
    }


    public void sendSelMsg(SelMsg selMsg) {
        String q_msg = RedisService.beanToString(selMsg);
        //l.info("send msg:"+q_msg);
        amqpTemplate.convertAndSend(RaConfig.Sel_Queue,q_msg);
    }
   /* public void send_m(Object msg){
        String q_msg = RedisService.beanToString(msg);
        //l.info("send msg:"+q_msg);
        amqpTemplate.convertAndSend(RaConfig.Queue_Name,q_msg);
    }

    //交换机传送
    public void send_t_m(Object msg){
        String q_msg = RedisService.beanToString(msg);
        //l.info("send msg:"+q_msg);
        amqpTemplate.convertAndSend(RaConfig.TOPP_EX,"t.key1",q_msg+"t1");
        amqpTemplate.convertAndSend(RaConfig.TOPP_EX,"t.key2",q_msg+"t2");
    }

    //广播传送
    public void send_f_m(Object msg){
        String q_msg = RedisService.beanToString(msg);
        l.info("send fanout msg:"+q_msg);
        amqpTemplate.convertAndSend(RaConfig.FANOUT_EX,"",q_msg);

    }

    //Header
    public void send_h_m(Object msg){
        String q_msg = RedisService.beanToString(msg);
        l.info("send headers msg:"+q_msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("head1","val1");
        properties.setHeader("head2","val2");
        Message mes = new Message(q_msg.getBytes(),properties);
        amqpTemplate.convertAndSend(RaConfig.HEADERS_EX,"",mes);

    }*/



}
