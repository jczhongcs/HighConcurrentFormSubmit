package com.high_con.grad.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.dao.UserDao;
import com.high_con.grad.entity.TCourse;
import com.high_con.grad.entity.User;
import com.high_con.grad.mapper.CourseMapper;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.UserKey;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.CourseService;
import com.high_con.grad.service.GoodsService;
import com.high_con.grad.service.UserService;
import com.high_con.grad.util.MD5_Util;
import com.high_con.grad.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CourseMapper courseMapper;


    @RequestMapping("/admin_manage")
    public String manage(HttpServletRequest request, HttpServletResponse response, Model model, User user){
        model.addAttribute("user", user);
        return "admin_manage";
    }

    //用户管理页面
    @RequestMapping("/usermanage")
    public String user_manage(HttpServletRequest request, HttpServletResponse response, Model model, User user, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "18")Integer pageSize,
                              UserSearchVo userSearchVo){
        model.addAttribute("user", user);

        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 18;
        }
        PageHelper.startPage(pageNum,pageSize);
        //System.out.println("id:"+userSearchVo.id);
        model.addAttribute("userSearchVo",userSearchVo);

        try{
        List<User> userList=userService.getUserBySearchVo(userSearchVo,pageNum,pageSize);
        model.addAttribute("userList",userList);
        PageInfo pageInfo = new PageInfo(userList,pageSize);
        model.addAttribute("pageInfo",pageInfo);
            //将页面信息传到前端
    }finally {
        PageHelper.clearPage();
    }
        return "usermanage";
    }

    //课程管理页面
    @RequestMapping("/coursemanage")
    public String coursemange(HttpServletRequest request, HttpServletResponse response, Model model, User user, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize) {
        model.addAttribute("user", user);
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;
        }
        try{
            List<CourseVo> courseList = courseService.listCourseVo(pageNum,pageSize);
            model.addAttribute("courseList",courseList);
            PageInfo pageInfo = new PageInfo(courseList,pageSize);//设置页面大小
            model.addAttribute("pageInfo",pageInfo);//将页面信息传到前端
            }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "coursemanage";
    }

    @RequestMapping("/userupdate/{userId}")
    public String updateUser(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("userId") Long userId){
        User user= userService.getById(userId);
        model.addAttribute("user", user);
        return "userupdate";
    }

    @RequestMapping(value = "/do_userupdate",method = RequestMethod.POST)
    @ResponseBody
    public Result<Long> updatedUser( @RequestParam("userId") Long userId
    ,@RequestParam("password") String password,@RequestParam("nickname") String nickname,@RequestParam("phone") String phone,
                                        @RequestParam("role")int role){
        User user= userService.getById(userId);
       // System.out.println("updating ok?");
        if(phone==null){
            phone=user.getPhone();
        }
        if(password.isEmpty()){
            password = user.getPassword();
        }else {
            password=MD5_Util.formPassToDbPass(password,user.getSalt());
        }
       //System.out.println("role:"+role);
        int updated=userDao.updateUser(userId,password,nickname,phone,role);
        System.out.println(updated);
        Long result=0L;
        //clear redis-cache清缓存
        redisService.del(UserKey.getid,""+userId);
        if(updated<1) result = 0L;
        return Result.success(result);
    }



    @RequestMapping("/useradd")
    public String addUser(HttpServletRequest request, HttpServletResponse response, Model model, User user){
        model.addAttribute("user",user);
        return "useradd";
    }


    @RequestMapping(value = "/do_useradd",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> insertUser( @RequestParam("userId") Long userId
            ,@RequestParam("password") String password,@RequestParam("nickname") String nickname,@RequestParam("phone") String phone,
                                     @RequestParam("role")int role){
        User user= userService.getById(userId);
        // System.out.println("updating ok?");
        if(password.isEmpty()||nickname.isEmpty()||userId==null){
            return Result.error(CodeMsg.USER_CREATED_FAILED);
        }else {
            password=MD5_Util.formPassToDbPass(password,"5zi7ng9");
        }
        //System.out.println("role:"+role);
        String salt="5zi7ng9";
        //System.out.println(updated);
        int result=userService.insertUser(userId,nickname,password,"5zi7ng9", new Date(),1,phone,role);

        //System.out.println("updated");
        //clear redis-cache
        if(result<1) return Result.error(CodeMsg.USER_CREATED_FAILED);

        return Result.success(2);

    }



    @DeleteMapping("/userdel/{userId}")
    public String delUser(@PathVariable("userId") Long userId,HttpServletResponse response){

        //System.out.println("deluser");
        userService.deleteUser(userId);
        return "redirect:/admin/usermanage";
    }


}
