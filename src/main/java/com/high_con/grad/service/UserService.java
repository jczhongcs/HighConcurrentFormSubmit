package com.high_con.grad.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.high_con.grad.SearchVo.UserSearchVo;
import com.high_con.grad.common.BaseService;
import com.high_con.grad.dao.UserDao;
import com.high_con.grad.entity.User;
import com.high_con.grad.exception.GlobeException;
import com.high_con.grad.mapper.UserMapper;
import com.high_con.grad.redis.RedisService;
import com.high_con.grad.redis.UserKey;
import com.high_con.grad.result.CodeMsg;
import com.high_con.grad.util.MD5_Util;
import com.high_con.grad.util.Session_Util;
import com.high_con.grad.util.User_Util;
import com.high_con.grad.vo.LoginVo;

import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    public static final String COOK_NAME_T = "token_";

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;


    public Boolean isSameUserInfo(User old_user,User new_user){
       // System.out.println(old_user);
        //System.out.println(new_user);
        //System.out.println(old_user.getRole()==new_user.getRole());
       // System.out.println(old_user.getPassword().equals(new_user.getPassword()));

        if(old_user.getId().equals(new_user.getId())&&old_user.getPassword().equals(new_user.getPassword()))
            {
                if(old_user.getGrade().equals(new_user.getGrade())&&old_user.getIdcard().equals(new_user.getIdcard())) {
                    if(old_user.getSex().equals(new_user.getSex())&&old_user.getDegree().equals(new_user.getDegree())) {
                        if(old_user.getIdcard().equals(new_user.getIdcard())&&old_user.getNation().equals(new_user.getNation())) {
                            if(old_user.getIsnormal().equals(new_user.getIsnormal())&&old_user.getPhone().equals(new_user.getPhone())) {
                                if(old_user.getNickname().equals(new_user.getNickname())&&old_user.getEmail().equals(new_user.getEmail())) {
                                    if(old_user.getEthnic().equals(new_user.getEthnic())&&old_user.getBirthday().equals(new_user.getBirthday())) {
                                        if(old_user.getPolitics().equals(new_user.getPolitics())&&old_user.getM1Name().equals(new_user.getM1Name())){
                                            if(old_user.getM1Phone().equals(new_user.getM1Phone())&&old_user.getM1Relate().equals(new_user.getM1Relate())) {
                                               if(old_user.getM2Name().equals(new_user.getM2Name())&&old_user.getM2Phone().equals(new_user.getM2Phone())){
                                                   if(old_user.getM2Relate().equals(new_user.getM2Relate())) {
                                                       //System.out.println("?");
                                                       return true;
                                                   }
                                               }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return  false;


    }

    public List<User> getUserBySearchVo(UserSearchVo userSearchVo,int pageNum,int pageSize){

        // System.out.println(userSearchVo);

        return userMapper.getUserListByPage(userSearchVo);
    }

    public int deleteUser(long id){
        return userDao.delete(id);
    }

    public int insertUser(long id, String nickname, String password, String salt, Date register, int logincount, String phone, int role){
        return userDao.insertUser(id,nickname,password,salt,register,logincount,phone,role);
    }

    public User getById(long id){

        User user = redisService.get(UserKey.getid,""+id,User.class);
        if(user!=null){
            //System.out.println("?");
            return user;
        }
        user =  userDao.getById(id);
        if(user!=null){
            redisService.set(UserKey.getid,""+id,user);
        }
        return user;
    }




    public boolean updatepwd(String token,long id,String pwd){
        User user = getById(id);
        if(user==null){
            throw new GlobeException(CodeMsg.PHONE_ERR);
        }
        User toUpd = new User();
        toUpd.setId(id);
        toUpd.setPassword(MD5_Util.formPassToDbPass(pwd,user.getSalt()));
        userDao.update(toUpd);
        //clear redis-cache
        redisService.del(UserKey.getid,""+id);
        user.setPassword(toUpd.getPassword());
        redisService.set(UserKey.tokenid,token,user);
        return true;
    }




    public String login(HttpServletRequest request ,HttpServletResponse response, LoginVo loginVo) {
        //System.out.println(loginVo.getRole());
        if(loginVo==null){
            throw new GlobeException( CodeMsg.SERVER_ERR);
        }

        String phone = loginVo.getPhone();
        String formPass =loginVo.getPwd();

        int role = loginVo.getRole();
        User user = getById(Long.parseLong(phone));

        if(user==null){
            throw new GlobeException(CodeMsg.USER_NOTEXISTS);
        }

        if(role!=user.getRole()){
            throw new GlobeException(CodeMsg.USER_NOTADMINORSTU);
        }

        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        //System.out.println("Pass:"+formPass);
        String cal_Pass = MD5_Util.formPassToDbPass(formPass,saltDB);
        //System.out.println("cal_Pass:"+cal_Pass);
        if(!cal_Pass.equals(dbPass)){
            throw new GlobeException( CodeMsg.PASSWORD_ERR);
        }
        //添加tok实现会话共享
        String tok = Session_Util.UUID();
        addCookie(user,tok,request,response);


        //addSession(user,request,response,tok);
        //System.out.println("tok:"+tok);
        return tok;
    }

    private void addCookie(User user,String tok,HttpServletRequest request,HttpServletResponse response){

        redisService.set(UserKey.tokenid,tok,user);
        Cookie cookie = new Cookie(COOK_NAME_T,tok);
        //String cookietoken = getCookieValue(request,COOK_NAME_T);
        //System.out.println("set cookietoken:"+cookietoken);
        cookie.setMaxAge(UserKey.tokenid.expire_time());
        cookie.setPath("/");
        response.addCookie(cookie);

    }


    public User getByToken(HttpServletRequest request,HttpServletResponse response,String token) {
        if(StringUtils.isNullOrEmpty(token)){
            return null;
        }

        User user = redisService.get(UserKey.tokenid,token,User.class);
        if(user!=null){
            addCookie(user,token,request,response);
            //addSession(user,request,response,token);
        }
        return user;
    }

    public List<User> getUsers(){
        List<User> users=new ArrayList<>();
        return (List<User>) redisService.getList(UserKey.getid.getPrefix(),User.class );
    }


    //Session部分
/*
    public User getByToken_Session(HttpServletRequest request,HttpServletResponse response,String token){
        if(StringUtils.isNullOrEmpty(token)){
            return null;
        }

        User user = redisService.get(UserKey.tokenid,token,User.class);
        if(user!=null){
            //addSession(user,request,response,token);
        }
        return user;
    }

    private void addSession(User user, HttpServletRequest request,HttpServletResponse response,String token){

        HttpSession session = request.getSession();
        session.setAttribute(COOK_NAME_T,token);
        //String val  = (String) session.getAttribute(COOK_NAME_T);
        //System.out.println("out put the session val:" + val);
        //System.out.println(val);
        redisService.set(UserKey.tokenid,token,user);

        session.setMaxInactiveInterval(UserKey.Token_Expire);
       // response.addCookie();
        *//*cookie.setMaxAge(UserKey.tokenid.expire_time());
        cookie.setPath("/");
        response.addCookie(cookie);*//*

    }*/


    private String getCookieValue(HttpServletRequest request, String cookNameT) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length<=0){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookNameT)){
                return cookie.getValue();
            }
        }
        return null;
    }

    public List<User> listUsersVo(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userDao.listUsersVo();
    }


    //Transaction可以保证sql的事务一致性，后面的数据库操作有误则一条都不执行。
    /*@Transactional
    public boolean tx() {
        User user = new User();
        user.setId(2);
        user.setName("xiaohuang");
        userDao.insert(user);



        return true;

    }*/
}
