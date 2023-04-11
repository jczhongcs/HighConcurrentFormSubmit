package com.high_con.grad.controller;


import com.high_con.grad.entity.User;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.UserService;
import com.high_con.grad.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/login")
@Controller
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/tologin")
    public String toLogin(){

        return "login";
    }


    /*@RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello,ok");
        //return new Result(0,"success","hello");
    }*/

    @RequestMapping("/dologin")
    @ResponseBody
    public Result<String>doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo){
       // System.out.println(loginVo.getRole());
        //log.info(loginVo.toString());
        String token = userService.login(request,response,loginVo);
        return Result.success(token);

        /*
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

   /* @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();

        return Result.success(true);
        //return new Result(0,"success","hello");
    }*/

   /* @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
       User user = redisService.get("12",""+1,User.class);

       return Result.success(user);
        //return new Result(0,"success","hello");
    }*/
   /* @RequestMapping("/redis/set")
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
