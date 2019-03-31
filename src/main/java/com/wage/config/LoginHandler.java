package com.wage.config;

import com.wage.bean.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandler implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null){
            httpServletResponse.sendRedirect("http://localhost:8083/tologin");
            //httpServletRequest.getRequestDispatcher("/tologin");
            return false;
        }else{
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user_token")){
                    Object user = redisTemplate.opsForValue().get(cookie.getValue());
                    if(user == null){
                        httpServletResponse.sendRedirect("http://localhost:8083/tologin");
                        return false;
                    }else{
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
