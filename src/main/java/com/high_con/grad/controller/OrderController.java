package com.high_con.grad.controller;


import com.high_con.grad.entity.Goods;
import com.high_con.grad.entity.Order;
import com.high_con.grad.entity.User;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.GoodsService;
import com.high_con.grad.service.OrderService;
import com.high_con.grad.service.UserService;
import com.high_con.grad.vo.GoodsVo;
import com.high_con.grad.vo.OrderDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/order")
@Controller
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/orderinfo")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, User user,
                                      @RequestParam("orderId")long orderId){

        model.addAttribute("user",user);
        if(user==null){
            return Result.error(CodeMsg.SESSION_ERR);
        }

        Order order = orderService.getOrderById(orderId);
        if(order==null){
            return Result.error(CodeMsg.ORDER_NOT_FOUND);
        }

        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoodsVo(goods);
        orderDetailVo.setOrder(order);
        return Result.success(orderDetailVo);
    }


}
