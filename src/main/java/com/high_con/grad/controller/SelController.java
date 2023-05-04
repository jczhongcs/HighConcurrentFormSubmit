package com.high_con.grad.controller;

import com.high_con.grad.common.User_Bean;
import com.high_con.grad.entity.*;
import com.high_con.grad.rab_m.SelMsg;
import com.high_con.grad.rab_m.Sender;
import com.high_con.grad.redis.CourseKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.*;
import com.high_con.grad.vo.CourseVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sel")
@RestController
public class SelController implements InitializingBean {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    CourseService courseService;
    @Autowired
    SelService selService;
    @Autowired
    C_OrderService c_orderService;
    @Autowired
    Sender sender;

    private Map<Long,Boolean> localMap = new HashMap<Long,Boolean>();

    //初始化缓存
    @Override
    public void afterPropertiesSet() throws Exception {
        List<CourseVo>courseVoList = courseService.listCourseVo();
        if(courseVoList==null){
            return;
        }
        for(CourseVo courseVo:courseVoList){
            redisService.set(CourseKey.getCourseStock,""+courseVo.getId(),courseVo.getCourseRemain());
            localMap.put(courseVo.getId(),false);
        }
    }



    //3600 tps
        //GET是保证幂等,无论结果多少次保证服务端数据保证不变，如果服务端数据不一致就用POST
    @RequestMapping(value = "/do_sel",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doSel(Model model, @User_Bean User user, @RequestParam("courseId")long courseId) {

        model.addAttribute("user", user);

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERR);
        }

        boolean over = localMap.get(courseId);
        if(over){
            return Result.error(CodeMsg.STOCK_EMP);
        }  //标记已经选完了

        long remain = redisService.decre(CourseKey.getCourseStock, "" + courseId);
        if (remain < 0) {
            localMap.put(courseId,true);
            return Result.error(CodeMsg.STOCK_EMP);
        }   //预减
        //判重
        Sel_Order sel_order = c_orderService.getSelCourseByUserIdCoursesId(user.getId(),courseId);
        if (sel_order != null) {
            return Result.error(CodeMsg.REP_SEL);
        }
        //入队
        SelMsg selMsg = new SelMsg();
        selMsg.setUser(user);
        selMsg.setCourseId(courseId);
        sender.sendSelMsg(selMsg);
        return Result.success(0);
    }





    @RequestMapping(value = "/do_sel2",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doSel2(Model model, @User_Bean User user, @RequestParam("courseId")long courseId
    ,@RequestParam(value = "mobile",required = false)String mobile,@RequestParam(value="grade",required = false)String grade,@RequestParam(value = "userId" ,required=false)Long userId
    ,@RequestParam(value = "chooseReason",required = false)String chooseReason) {

        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERR);
        }
        //标记已经选完了
        boolean over = localMap.get(courseId);
        if(over){
            return Result.error(CodeMsg.STOCK_EMP);
        }
        //预减
        long remain = redisService.decre(CourseKey.getCourseStock, "" + courseId);
        if (remain < 0) {
            localMap.put(courseId,true);
            return Result.error(CodeMsg.STOCK_EMP);
        }
        //判重
        Sel_Order sel_order = c_orderService.getSelCourseByUserIdCoursesId(user.getId(),courseId);

        if (sel_order != null) {

            return Result.error(CodeMsg.REP_SEL);
        }
        //入队
        SelMsg selMsg = new SelMsg();
        selMsg.setUser(user);
        selMsg.setCourseId(courseId);
        selMsg.setUserChooseReason(chooseReason);
        sender.sendSelMsg(selMsg);
        return Result.success(0);
    }


        @RequestMapping(value = "/result",method = RequestMethod.GET)
        @ResponseBody
        public Result<Long> selresult(Model model, @User_Bean User user, @RequestParam("courseId")long courseId){
            model.addAttribute("user",user);
            if(user==null) {
                return Result.error(CodeMsg.SESSION_ERR);
            }

            long result = selService.getSelResult(user.getId(),courseId);

            return Result.success(result);
        }
        //成功回应对应表中id，否则返回-1.

}




