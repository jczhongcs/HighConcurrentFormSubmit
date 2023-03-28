package com.high_con.grad.controller;


import com.high_con.grad.entity.Kill_Order;
import com.high_con.grad.entity.Order;
import com.high_con.grad.entity.User;
import com.high_con.grad.rab_m.KillMsg;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.redis.ArticleKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.GoodsService;
import com.high_con.grad.service.KillService;
import com.high_con.grad.service.OrderService;
import com.high_con.grad.service.UserService;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kill")
public class KillController implements InitializingBean {


    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    KillService killService;

    @Autowired
    Sender sender;

    private Map<Long,Boolean> localMap = new HashMap<Long,Boolean>();

    //初始化缓存
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo>goodsVoList = goodsService.listGoodsVo();
        if(goodsVoList==null){
            return;
        }
        for(GoodsVo goodsVo:goodsVoList){
            redisService.set(ArticleKey.getKillStock,""+goodsVo.getId(),goodsVo.getStockCount());
            localMap.put(goodsVo.getId(),false);
        }
    }


    //5000 process
    //2600 tps
        //GET是保证幂等,无论结果多少次保证服务端数据保证不变，如果服务端数据不一致就用POST
    @RequestMapping(value = "/do_kill",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> dokill(Model model, User user, @RequestParam("goodsId")long goodsId) {

        model.addAttribute("user", user);

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERR);
        }
        //标记已经结束了
        boolean over = localMap.get(goodsId);
        if(over){
            return Result.error(CodeMsg.STOCK_EMP);
        }
        //预减
        long stock = redisService.decre(ArticleKey.getKillStock, "" + goodsId);
        if (stock < 0) {
            localMap.put(goodsId,true);
            return Result.error(CodeMsg.STOCK_EMP);
        }
        //判重
        Kill_Order order = orderService.getKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REP_KILL);
        }
        //入队
        KillMsg killMsg = new KillMsg();
        killMsg.setUser(user);
        killMsg.setGoodsId(goodsId);
        sender.sendKillMsg(killMsg);

        return Result.success(0);
    }
        /*GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if(stock<=0){
            return Result.error(CodeMsg.STOCK_EMP);
        }
        Kill_Order order = orderService.getKillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            return Result.error(CodeMsg.REP_KILL);
        }
        Order orderinfo = killService.kill(user,goodsVo);
        return Result.success(orderinfo);*/

        @RequestMapping(value = "/result",method = RequestMethod.GET)
        @ResponseBody
        public Result<Long> killresult(Model model, User user, @RequestParam("goodsId")long goodsId){
            model.addAttribute("user",user);
            if(user==null) {
                return Result.error(CodeMsg.SESSION_ERR);
            }
            long result = killService.getKillResult(user.getId(),goodsId);
            //System.out.println("result:"+result);
            return Result.success(result);
        }
        //成功id，失败-1

}




