package com.high_con.grad.service;

import com.high_con.grad.entity.Goods;
import com.high_con.grad.entity.Kill_Order;
import com.high_con.grad.entity.Order;
import com.high_con.grad.entity.User;
import com.high_con.grad.redis.KillKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;


    @Transactional
    public Order kill(User user, GoodsVo goodsVo) {

        boolean success = goodsService.reduceStock(goodsVo);
        if (success) {
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
        //System.out.println("reduction is OK");
    }



    public long getKillResult(Long userid, long goodsId) {
        Kill_Order order = orderService.getKillOrderByUserIdGoodsId(userid, goodsId);
        if (order != null) {
            //System.out.println(order.getOrderId());
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } 
            else {
                return 0;
            }
        }

    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exist(KillKey.isGoodsSoldout,""+goodsId);

    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(KillKey.isGoodsSoldout,""+goodsId,true);
    }

}
