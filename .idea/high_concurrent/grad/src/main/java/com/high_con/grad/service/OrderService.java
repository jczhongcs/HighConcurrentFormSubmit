package com.high_con.grad.service;

import com.high_con.grad.dao.OrderDao;
import com.high_con.grad.entity.Kill_Order;
import com.high_con.grad.entity.Order;
import com.high_con.grad.entity.User;
import com.high_con.grad.redis.OrderKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;


    public Kill_Order getKillOrderByUserIdGoodsId(long userId, long goodsId) {
        //System.out.println("userid get,goodsid get");
        //return orderDao.getKillOrderByUserIdGoodsId(userId,goodsId);
        //System.out.println("取到了");
        return redisService.get(OrderKey.getKillOrderByUidGid,""+userId+"_"+goodsId,Kill_Order.class);


    }

    public Order getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);

    }


    @Transactional
    public Order createOrder(User user, GoodsVo goodsVo) {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setDeliveryAddrId(0L);
        order.setGoodsId(goodsVo.getId());
        order.setUserId(user.getId());
        order.setGoodsCount(1);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsPrice(goodsVo.getKillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);

        orderDao.insert(order);
        Kill_Order kill_order = new Kill_Order();
        kill_order.setGoodsId(goodsVo.getId());
        kill_order.setOrderId(order.getId());
        kill_order.setUserId(user.getId());

        //System.out.println("set order is ok");

        orderDao.insertKillOrder(kill_order);
        redisService.set(OrderKey.getKillOrderByUidGid,""+user.getId()+"_"+goodsVo.getId(),kill_order);

        return order;

    }


}
