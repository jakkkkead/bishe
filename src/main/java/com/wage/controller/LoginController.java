package com.wage.controller;
import com.alibaba.fastjson.JSONObject;
import com.wage.bean.PageBean;
import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.bean.TUser;
import com.wage.service.LoginService;
import com.wage.util.Md5Util;
import com.wage.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("tologin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("tosigin")
    public String toSigin(){
        return "sigin";
    }
    @RequestMapping("sigin")
    @ResponseBody
    public RestResult singin(TUser user, BindingResult result){
        String name = loginService.checkName(user.getName());
        if(name !=null){
            return RestResultGenerator.createFailResult(name);
        }
        loginService.sigin(user);
        return RestResultGenerator.createOkResult();
    }
    @RequestMapping("login")
    @ResponseBody
    public RestResult login(TUser user , HttpSession session , HttpServletResponse response){
        if( loginService.login(user)){
            String token = UuidUtil.getUUID();
            Cookie cookie = new Cookie("user_token",token);
            cookie.setMaxAge(24*7 * 3600);// 设置存在时间为7天
            cookie.setPath("/");//设置作用域
            response.addCookie(cookie);
            JSONObject json = (JSONObject) JSONObject.toJSON(user);
            redisTemplate.opsForValue().set(token,json,1, TimeUnit.DAYS);//设置存活时间为1天
            session.setAttribute(token,user);
            return RestResultGenerator.createOkResult();
        }else{
            return RestResultGenerator.createFailResult();
        }

    }
    @RequestMapping("logout")
    public String logout(HttpSession session , HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user_token")){
                String token = cookie.getValue();
                //设置用户信息过期
                redisTemplate.expire(token,0,TimeUnit.SECONDS);
                session.setAttribute(token,null);
                return "tologin";
            }
        }
        return "tologin";
    }


    /**
     * 获取用户列表（条件查询)
     * @param currentPage
     * @param pageSize
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping("getUsers")
    @ResponseBody
    public RestResult getUserInfo(int currentPage,int pageSize,String beginDate,String endDate,Integer departId){
        if(departId !=null && departId ==5){
            departId = null;
        }
        if(beginDate.equals("")){
            beginDate =null;
        }
        if(endDate.equals("")){
            endDate =null;
        }
        PageBean obj = loginService.getUsers(currentPage,pageSize,beginDate,endDate,departId);
        return RestResultGenerator.createOkResult(obj);

    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    @ResponseBody
    public RestResult updateUser(TUser user){
//        String name = loginService.checkName(user.getName());
//        if(name !=null){
//            return RestResultGenerator.createFailResult(name);
//        }
        TUser dbUser = loginService.getUserById(user.getUserId());
        if(!user.getName().equals(dbUser.getName())){
            String name = loginService.checkName(user.getName());
            if(name !=null){
                return RestResultGenerator.createFailResult(name);
            }
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            user.setPassword(Md5Util.MD5(user.getPassword()));
        }
        int i = loginService.updateUser(user);
        if(i > 0){
            return RestResultGenerator.createOkResult();
        }
        return RestResultGenerator.createFailResult();

    }
    @RequestMapping("getUserForm")
    @ResponseBody
    public RestResult getUserForm(String year, Integer departId){
        if(departId !=null && departId ==5){
            departId = null;
        }
        Object obj = loginService.getUserForm(year,departId);

        return RestResultGenerator.createOkResult(obj);

    }
    @RequestMapping("getUserName")
    @ResponseBody
    public RestResult getUserName(HttpSession session) {
        TUser user = (TUser) session.getAttribute("user");
        if (user == null) {
            return RestResultGenerator.createFailResult("请先登录！");
        }else{
            return RestResultGenerator.createOkResult(user);
        }
    }




}
