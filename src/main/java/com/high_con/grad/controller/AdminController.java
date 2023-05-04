package com.high_con.grad.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.common.User_Bean;
import com.high_con.grad.dao.UserDao;
import com.high_con.grad.entity.*;
import com.high_con.grad.mapper.CourseMapper;
import com.high_con.grad.mapper.SelCourseMapper;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.rab_m.UserMsg;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.UserKey;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.C_OrderService;
import com.high_con.grad.service.CourseService;
import com.high_con.grad.service.FeedbackService;
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
    FeedbackService feedbackService;

    @Autowired
    C_OrderService c_orderService;


    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SelCourseMapper selCourseMapper;

    @Autowired
    Sender sender;

    @RequestMapping("/admin_manage")
    public String manage(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user){
        model.addAttribute("user", user);
        return "admin_manage";
    }

    //用户管理页面
    @RequestMapping("/usermanage")
    public String user_manage(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "12")Integer pageSize,
                            UserSearchVo userSearchVo){
        model.addAttribute("user", user);
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 12;
        }
        PageHelper.startPage(pageNum,pageSize);
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

    @RequestMapping("/courseadd")
    public String addCourse(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user){

        return "courseadd";
    }

    @DeleteMapping("/coursedel/{id}")
    public String CourseDel(@PathVariable("id") Long id,HttpServletResponse response){
        //System.out.println("deluser");

        //System.out.println(c_order);
        courseService.deleteCourse(id);


        return "redirect:/admin/coursemanage";
    }


    @RequestMapping("/courseadded")
    public Result<Integer> addedCourse(HttpServletRequest request, HttpServletResponse response, Model model,TCourse course){
        course.setCourseTitle(course.getCourseName());
        course.setCourseStartDate(course.getStartDate());
        course.setCourseEndDate(course.getEndDate());
        User user=userService.getById(course.getTeacherId());
        course.setTeacherName(user.getNickname());
        int result=courseMapper.insert(course);
        course = courseMapper.selectOne(course);
        System.out.println(course);
        //System.out.println("updated");
        SelCourse selCourse= new SelCourse();
        selCourse.setCourseRemain(course.getCourseStock());
        selCourse.setStartTime(course.getStartDate());
        selCourse.setEndTime(course.getEndDate());
        selCourse.setCourseId(course.getId());
        result=selCourseMapper.insert(selCourse);
        selCourse=selCourseMapper.selectOne(selCourse);
        System.out.println(selCourse);
        return Result.success(2);
    }


    @RequestMapping("/courseupdate/{Id}")
    public String updateCourse(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("Id") Long courseId){
        TCourse course =courseService.getCourse(courseId);
        model.addAttribute("course", course);
        return "courseupdate";
    }

    //课程管理页面
    @RequestMapping("/coursemanage")
    public String coursemange(HttpServletRequest request, HttpServletResponse response, Model model, @User_Bean User user, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize) {
        model.addAttribute("user", user);
       // System.out.println("userid:"+user.getId());
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 12;
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

    @RequestMapping("/seled_course")
    public String SeleledCourse(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {

        model.addAttribute("user", user);
        if (pageNum == null) {//若pageNum为空
            pageNum = 1;   //设置默认当前页为1
        }
        if (pageNum <= 0) {  //若为负数
            pageNum = 1;   //设置默认值为1
        }
        if (pageSize == null) { //若页面大小为空
            pageSize = 5;    //设置默认每页显示的数据数
        }
        PageHelper.startPage(pageNum,pageSize);
        try {
            //调用分页查询的方法
            List<CourseVo> courseList = courseService.listCourseByAdmin();

            model.addAttribute("coursesList", courseList);

            PageInfo pageInfo = new PageInfo(courseList, pageSize);//设置页面大小
            model.addAttribute("pageInfo", pageInfo);//将页面信息传到前端
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "adminseledcourse";
    }


    @RequestMapping("/courseinfo/{id}")
    public String seledcourseinfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("id") Long courseId,@User_Bean User user){
        C_Order course = c_orderService.getCourseById(courseId);
        // System.out.println(course);
        User user1 = userService.getById(course.getUserId());
        model.addAttribute("user",user1);
        model.addAttribute("course",course);
        return "admincourseinfo";
    }


    @DeleteMapping("/seldel/{id}")
    public String selDel(@PathVariable("id") Long id,HttpServletResponse response){
        //System.out.println("deluser");

        //System.out.println(c_order);
        c_orderService.deleteOrder(id);

        return "redirect:/admin/seled_course";
    }


    @RequestMapping("/feedbackinfo/{id}")
    public String feedbackinfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("id") Long feedbackId,@User_Bean User user){
        Feedback feedback=feedbackService.getByfeedbackid(feedbackId);
        User user1 =userService.getById( feedback.getUserId());
        model.addAttribute("user",user1);
        model.addAttribute("feedback",feedback);
        return "feedbackinfo";
    }

    @DeleteMapping("/feedbackdel/{id}")
    public String delFeed(@PathVariable("id") Long id,HttpServletResponse response){
        //System.out.println("delFeed");

        feedbackService.deleteFeedback(id);
        return "redirect:/admin/feedbackmanage";
    }

    //反馈管理页面
    @RequestMapping("/feedbackmanage")
    public String feedmange(HttpServletRequest request, HttpServletResponse response, Model model, @User_Bean User user,@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){

        model.addAttribute(user);
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 12;
        }
        PageHelper.startPage(pageNum,pageSize);
        try {
            //System.out.println(user.getId());
            List<Feedback> feedbackList = feedbackService.getFeedList();
            //System.out.println(feedbackList.get(0));
            model.addAttribute("feedbackList", feedbackList);
            PageInfo pageInfo = new PageInfo(feedbackList,pageSize);
            model.addAttribute("pageInfo",pageInfo);//
        }finally {
            PageHelper.clearPage();
        }

        return "feedbackmanage";
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
                                        @RequestParam("role")int role,@RequestParam(value = "grade",required = false)String grade){
        User user= userService.getById(userId);
       // System.out.println("updating ok?");

        if(password.isEmpty()){
            password = user.getPassword();
        }else {
            password=MD5_Util.inputPassToDbPass(password,user.getSalt());
        }
       //System.out.println("role:"+role);
        user.setPassword(password);
        UserMsg userMsg = new UserMsg();
        //userMsg.setUser(user);
        sender.sendUpdateMsg(userMsg);
        //int updated=userDao.updateUser(userId,password,nickname,phone,role,grade);

        //System.out.println(updated);
        Long result=1L;
        // redis清缓存
        redisService.del(UserKey.getid,""+userId);
        //if(updated<1) result = 0L;
        return Result.success(result);
    }

    @RequestMapping(value = "/do_userupdate2",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> updatedUser2(User user){

        System.out.println(user);
        User user1= userService.getById(user.getId());
        user.setSalt(user1.getSalt());
        user.setRegisterDate(user1.getRegisterDate());
        // System.out.println("updating ok?");
        if(user.getPassword().isEmpty()){
            user.setPassword(user1.getPassword());
        }else {
           user.setPassword(MD5_Util.inputPassToDbPass(user.getPassword(),user.getSalt()));
        }
        //System.out.println("role:"+role);
        UserMsg userMsg = new UserMsg();
        userMsg.setUser1(user);
        userMsg.setUser2(null);
        sender.sendUpdateMsg(userMsg);
        //int updated=userDao.updateUser(userId,password,nickname,phone,role,grade);
        //System.out.println(updated);
        Long result=1L;
        // redis清缓存
        redisService.del(UserKey.getid,""+user.getId());
        //if(updated<1) result = 0L;
        return Result.success(1);
    }

    @RequestMapping("/useradd")
    public String addUser(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user){
        model.addAttribute("user",user);
        return "useradd";
    }

    @RequestMapping(value = "/do_useradd",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> insertUser( @RequestParam("userId") Long userId
            ,@RequestParam("password") String password,@RequestParam("nickname") String nickname,@RequestParam("phone") String phone,
                                     @RequestParam("role")int role){

        // System.out.println("updating ok?");
        if(password.isEmpty()||nickname.isEmpty()||userId==null){
            return Result.error(CodeMsg.USER_CREATED_FAILED);
        }else {
           password=MD5_Util.inputPassToDbPass(password,"5zi7ng9");
        }
        //System.out.println("role:"+role);
        String salt="5zi7ng9";
        //System.out.println(updated);
        int result=userService.insertUser(userId,nickname,password,salt, new Date(),1,phone,role);
        //System.out.println("updated");

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
