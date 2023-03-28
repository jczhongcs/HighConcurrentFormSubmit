package com.high_con.grad.controller;


import com.high_con.grad.entity.User;
import com.high_con.grad.redis.ArticleKey;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.result.Result;
import com.high_con.grad.service.GoodsService;
import com.high_con.grad.service.UserService;
import com.high_con.grad.vo.ArticleDetailVo;
import com.high_con.grad.vo.GoodsVo;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RequestMapping("/article")
@Controller
public class ArticleController {

    private static Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    //吞吐量 4900
    //测试5000用户 * 10次访问


    @RequestMapping(value = "/to_article", produces = "text/html")
    @ResponseBody
    public String toArticle(HttpServletRequest request, HttpServletResponse response, Model model, User user) {

        model.addAttribute("user", user);

        String page = redisService.get(ArticleKey.getGL, "", String.class);

        if (!StringUtils.isNullOrEmpty(page)) {
            return page;
        }

        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);


        //return "article_li";
        IWebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        page = thymeleafViewResolver.getTemplateEngine().process("article_li", context);
        if (!StringUtils.isNullOrEmpty(page)) {
            redisService.set(ArticleKey.getGL, "", page);
        }
        return page;
    }



    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<ArticleDetailVo> Detail(HttpServletRequest request, HttpServletResponse response, Model model, User user,
                                          @PathVariable("goodsId") long goodsId) {

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        long start = goodsVo.getStartDate().getTime();
        long end = goodsVo.getEndDate().getTime();
        long right_time = System.currentTimeMillis();

        //Date getdate = goodsVo.getStartDate();
        //System.out.println(getdate);

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

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();

        articleDetailVo.setStatus(right_status);
        //System.out.println(user.getId());
        articleDetailVo.setUser(user);

        articleDetailVo.setRemain(remain);

        articleDetailVo.setGoodsVo(goodsVo);

        // System.out.println("remain:"+remain);


        //return "g_detail";


        return Result.success(articleDetailVo);

    }


    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String Detail2(HttpServletRequest request, HttpServletResponse response, Model model, User user,
                          @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);


        long start = goodsVo.getStartDate().getTime();
        long end = goodsVo.getEndDate().getTime();
        long right_time = System.currentTimeMillis();

        Date getdate = goodsVo.getStartDate();
        System.out.println(getdate);

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
        // System.out.println("remain:"+remain);
        model.addAttribute("status", right_status);
        model.addAttribute("remain", remain);

        //return "g_detail";

        String page = redisService.get(ArticleKey.getGT, "" + goodsId, String.class);

        if (!StringUtils.isNullOrEmpty(page)) {
            return page;
        }

        IWebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        page = thymeleafViewResolver.getTemplateEngine().process("g_detail", context);
        if (!StringUtils.isNullOrEmpty(page)) {
            redisService.set(ArticleKey.getGT, "" + goodsId, page);
        }
        return page;

    }




}