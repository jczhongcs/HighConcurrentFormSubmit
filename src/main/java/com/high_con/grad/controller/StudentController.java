package com.high_con.grad.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.high_con.grad.common.User_Bean;
import com.high_con.grad.entity.*;
import com.high_con.grad.mapper.FeedbackMapper;
import com.high_con.grad.mapper.UserMapper;
import com.high_con.grad.rab_m.FeedMsg;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.rab_m.UserMsg;
import com.high_con.grad.redis.FeedbackKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.UserKey;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.*;
import com.high_con.grad.util.MD5_Util;
import com.high_con.grad.util.Session_Util;
import com.high_con.grad.vo.CourseDetailVo;
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

@RequestMapping("/stumanage")
@Controller

public class StudentController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    C_OrderService c_orderService;

    @Autowired
    CourseService courseService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserMapper userMapper;

    @Autowired
    Sender sender;


    @RequestMapping("/home")
    public String manage(HttpServletRequest request, HttpServletResponse response, Model model, @User_Bean User user){
        model.addAttribute("user", user);

        return "stu_manage";
    }

    @RequestMapping("/userinfo")
    public String updateUser(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user){
        User user1= userService.getById(user.getId());
        model.addAttribute("user", user1);
        return "userinfo";
    }





    @RequestMapping("/feedback")
    public String feedback(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user,@RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
        User user1= userService.getById(user.getId());
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
            List<Feedback> feedbackList = feedbackService.getFeedListByUserId(user.getId());
            //System.out.println(feedbackList.get(0));
            model.addAttribute("feedbackList", feedbackList);
            PageInfo pageInfo = new PageInfo(feedbackList,pageSize);
            model.addAttribute("pageInfo",pageInfo);//
        }finally {
            PageHelper.clearPage();
        }


        return "feedbacklist";
    }

    @RequestMapping("/to_feedback")
    public String tofeedback(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user){
        User user1= userService.getById(user.getId());
        Feedback feedback = new Feedback();
        model.addAttribute("feedback",feedback);
        model.addAttribute("user", user1);
        return "feedbacksubmit";
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
        //System.out.println("deluser");

        feedbackService.deleteFeedback(id);
        return "redirect:/stumanage/feedback";
    }

    @RequestMapping("/feedbacksubmit")
    @ResponseBody
    public Result<Integer> feedbackSubmit(Feedback feedback,@User_Bean User user){

        //System.out.println(feedback.getCourseSat());
        feedback.setUuid(Session_Util.UUID());

       // System.out.println("1");
        feedbackMapper.insert(feedback);

        return Result.success(1);
    }

    @RequestMapping("/feedbacksubmit2")
    @ResponseBody
    public Result<Integer> feedbackSubmit2(Feedback feedback,@User_Bean User user){


        feedback.setUuid(Session_Util.UUID());
       /* if(feedbackService.isSameFromSameUser(feedback)){
            System.out.println("?");
            return Result.error(CodeMsg.REP_SEL);
        }*/
        //System.out.println("2");
        FeedMsg feedMsg = new FeedMsg();
        feedMsg.setFeedback(feedback);
        sender.sendFeedMsg(feedMsg);
        //System.out.println(feedback);

        return Result.success(1);
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
            List<CourseVo> courseList = courseService.listCourseByUserId(user.getId());

            model.addAttribute("coursesList", courseList);

            PageInfo pageInfo = new PageInfo(courseList, pageSize);//设置页面大小
            model.addAttribute("pageInfo", pageInfo);//将页面信息传到前端
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return "seledcourse";
    }

    @RequestMapping("/courseinfo/{id}")
    public String seledcourseinfo(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("id") Long courseId,@User_Bean User user){
        C_Order course = c_orderService.getCourseById(courseId);
       // System.out.println(course);
        model.addAttribute("user",user);
        model.addAttribute("course",course);
        return "courseinfo";
    }


    @DeleteMapping("/seldel/{id}")
    public String selDel(@PathVariable("id") Long id,HttpServletResponse response){
        //System.out.println("deluser");

        //System.out.println(c_order);
        c_orderService.deleteOrder(id);

        return "redirect:/stumanage/seled_course";
    }


    @RequestMapping(value = "/do_userupdate2",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> updatedUser2(User user,@User_Bean User user_login){
        //System.out.println(user);
        User user1= userService.getById(user.getId());
        user.setSalt(user1.getSalt());
        user.setRegisterDate(user1.getRegisterDate());
        user.setRole(1);

        if(user.getPassword().isEmpty()){
            user.setPassword(user1.getPassword());
        }else {
            user.setPassword(MD5_Util.inputPassToDbPass(user.getPassword(),user.getSalt()));
        }
        UserMsg userMsg = new UserMsg();
       if(userService.isSameUserInfo(user,user1)){
            //System.out.println("same");
            return Result.success(1);
        }
        userMsg.setUser1(user);
        userMsg.setUser2(user1);
        sender.sendUpdateMsg(userMsg);

        redisService.del(UserKey.getid,""+user.getId());

        return Result.success(1);
    }


    @RequestMapping(value = "/do_userupdate",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> updatedUser(User user,@User_Bean User user_login){
        //System.out.println(user);
        User user1= userService.getById(user.getId());
        user.setSalt(user1.getSalt());
        user.setRegisterDate(user1.getRegisterDate());
        user.setRole(1);
        //System.out.println("?");
        if(user.getPassword().isEmpty()){
            user.setPassword(user1.getPassword());
        }else {
            user.setPassword(MD5_Util.inputPassToDbPass(user.getPassword(),user.getSalt()));
        }

        userMapper.updateByPrimaryKey(user);

        return Result.success(1);
    }

    @RequestMapping("/to_sel")
    public String toSel(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {

        model.addAttribute("user", user);
        if(pageNum == null){//若pageNum为空
            pageNum = 1;   //设置默认当前页为1
        }
        if(pageNum <= 0){  //若为负数
            pageNum = 1;   //设置默认值为1
        }
        if(pageSize == null){ //若页面大小为空
            pageSize = 5;    //设置默认每页显示的数据数
        }
        try {
            //调用分页查询的方法
            List<CourseVo> courseList = courseService.listCourseVo(pageNum,pageSize);

            model.addAttribute("coursesList", courseList);

            PageInfo pageInfo = new PageInfo(courseList,pageSize);//设置页面大小
            model.addAttribute("pageInfo",pageInfo);//将页面信息传到前端
        }finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }


        /*String page = redisService.get(ArticleKey.getGL, "", String.class);

        if (!StringUtils.isNullOrEmpty(page)) {
            return page;
        }
*/

        //return "article_li";
       /* IWebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        page = thymeleafViewResolver.getTemplateEngine().process("article_li", context);
        if (!StringUtils.isNullOrEmpty(page)) {
            redisService.set(ArticleKey.getGL, "", page);
        }*/
        /*return page;*/
        return "to_sel";
    }



    @RequestMapping(value = "/detail/{courseId}")
    @ResponseBody
    public Result<CourseDetailVo> Detail(HttpServletRequest request, HttpServletResponse response, Model model,@User_Bean User user,
                                         @PathVariable("courseId") long courseId) {

        CourseVo courseVo= courseService.getCoursesVoByCoursesId(courseId);
        //System.out.println(courseVo.getCourseRemain());
        long start = courseVo.getStartDate().getTime();
        long end = courseVo.getEndDate().getTime();
        long right_time = System.currentTimeMillis();


        int remain = 0;
        int right_status = 0;
        if (right_time < start) {
            right_status = 0;
            remain = (int) (start - right_time) / 1000;
        } else if (right_time > end) {
            right_status = 2; //结束
            remain = -1;
        } else {
            right_status = 1;
            remain = 0;
        }
        Sel_Order sel_order = c_orderService.getSelCourseByUserIdCoursesId(user.getId(),courseId);

        CourseDetailVo courseDetailVo = new CourseDetailVo();
        if (sel_order != null)
            courseDetailVo.setIsSelected(1);
        else courseDetailVo.setIsSelected(0);
        if(courseVo.getCourseRemain()==0){
            courseDetailVo.setCourseStock(0);
        }


        courseDetailVo.setStatus(right_status);
        courseDetailVo.setUser(user);
        courseDetailVo.setRemain(remain);

        courseDetailVo.setCourseVo(courseVo);

       /*
        articleDetailVo.setStatus(right_status);
        //System.out.println(user.getId());
        articleDetailVo.setUser(user);
        articleDetailVo.setRemain(remain);
        articleDetailVo.setGoodsVo(goodsVo);
*/
        // System.out.println("remain:"+remain);
        //return "g_detail";
        return Result.success(courseDetailVo);

    }
}
