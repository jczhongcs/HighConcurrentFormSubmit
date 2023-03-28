package com.high_con.grad.config;

import com.high_con.grad.entity.User;
import com.high_con.grad.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> cla = parameter.getParameterType();
        return cla == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String ckToken = getCookieValue(request,UserService.COOK_NAME_T);
        String paramToken = request.getParameter(UserService.COOK_NAME_T);

       if(StringUtils.isNullOrEmpty(ckToken)&&StringUtils.isNullOrEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isNullOrEmpty(paramToken)?ckToken:paramToken;


         return userService.getByToken(request,response,token);

    }

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
}
