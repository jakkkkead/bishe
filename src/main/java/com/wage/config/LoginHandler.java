package com.wage.config;

import com.wage.bean.TUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoginHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        String origin = httpServletRequest.getHeader("Origin");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        TUser user = (TUser) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
           httpServletResponse.sendRedirect("http://localhost:8083/tologin");
          //  httpServletRequest.getRequestDispatcher("/tologin");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
