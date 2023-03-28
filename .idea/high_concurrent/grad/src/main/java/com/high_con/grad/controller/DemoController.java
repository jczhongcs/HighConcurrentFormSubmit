package com.high_con.grad.controller;

import com.high_con.grad.entity.User;
import com.high_con.grad.rab_m.Receiver;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.UserKey;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/demo")
@Controller
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    Sender sender;

    @Autowired
    Receiver receiver;


    @ResponseBody
    String home(){
        return "Hello World!";
    }


    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello,ok");
        //return new Result(0,"success","hello");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String>error(){
         return Result.error(CodeMsg.SERVER_ERR);
        /*return new Result(400102,"XXX");
        return new Result(400101,"XXX");
        return new Result(400100,"session失效");*/
    }


    @RequestMapping("/thymeleaf")

    public String thymeleaf(Model model){
        model.addAttribute("name","Hzy");
        return "hello";

    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);

        return Result.success(user);
        //return new Result(0,"success","hello");
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        //userService.tx();

        return Result.success(true);
        //return new Result(0,"success","hello");
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.tokenid,""+1,User.class);

        return Result.success(user);
        //return new Result(0,"success","hello");
    }



  /*  @RequestMapping("/mqtest")
    @ResponseBody
    public Result<String> mqtest(){
        sender.send_m("hello!");
        return Result.success("Hello");
    }

    @RequestMapping("/mq/top")
    @ResponseBody
    public Result<String> toppictest(){
        sender.send_t_m("hello!");
        return Result.success("Hello");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanouttest(){
        sender.send_f_m("hello!");
        return Result.success("Hello");
    }

    @RequestMapping("/mq/headers")
    @ResponseBody
    public Result<String> headerstest(){
        sender.send_h_m("hello!");
        return Result.success("Hello");
    }*/

    /*@RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,""+1,user);
        return Result.success(true);
        //return new Result(0,"success","hello");
    }*/


}
