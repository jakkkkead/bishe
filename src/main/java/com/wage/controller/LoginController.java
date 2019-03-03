package com.wage.controller;

import com.wage.bean.PageBean;
import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.bean.TUser;
import com.wage.service.LoginService;
import com.wage.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
@CrossOrigin
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
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
    public RestResult login(TUser user , HttpSession session){
        if( loginService.login(user)){
            session.setAttribute("user",user);
            return RestResultGenerator.createOkResult();
        }else{
            return RestResultGenerator.createFailResult();
        }

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
    public RestResult getUserInfo(int currentPage,int pageSize,String beginDate,String endDate){
        if(beginDate.equals("")){
            beginDate =null;
        }
        if(endDate.equals("")){
            endDate =null;
        }
        PageBean obj = loginService.getUsers(currentPage,pageSize,beginDate,endDate);
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




}
