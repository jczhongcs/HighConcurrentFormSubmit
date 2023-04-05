package com.high_con.grad.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.high_con.grad.entity.Sel_Order;
import com.high_con.grad.entity.User;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.redis.ArticleKey;
import com.high_con.grad.redis.CourseKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.*;
import com.high_con.grad.vo.ArticleDetailVo;
import com.high_con.grad.vo.CourseDetailVo;
import com.high_con.grad.vo.CourseVo;
import com.high_con.grad.vo.GoodsVo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
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
    GoodsService goodsService;

    @Autowired
    C_OrderService c_orderService;

    @Autowired
    CourseService courseService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;


    @RequestMapping("/home")
    public String manage(HttpServletRequest request, HttpServletResponse response, Model model, User user){
        model.addAttribute("user", user);

        return "stu_manage";
    }



    @RequestMapping("/to_sel")
    public String toSel(HttpServletRequest request, HttpServletResponse response, Model model, User user, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {

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
    public Result<CourseDetailVo> Detail(HttpServletRequest request, HttpServletResponse response, Model model, User user,
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
