package com.high_con.grad.controller;


import com.high_con.grad.entity.User;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@Controller
@RestController
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;



    @RequestMapping("/inf")
    @ResponseBody
    public Result<User> info(Model model, User user){
        model.addAttribute("user",user);

        return Result.success(user);
    }


}
