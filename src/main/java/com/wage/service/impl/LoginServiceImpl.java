package com.wage.service.impl;


import com.wage.bean.ExpenseForms;
import com.wage.bean.PageBean;
import com.wage.bean.TUser;
import com.wage.dao.TUserMapper;
import com.wage.service.LoginService;
import com.wage.util.FormUtils;
import com.wage.util.Md5Util;
import com.wage.util.TimeUtils;
import com.wage.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public void sigin(TUser user) {
        user.setPassword(Md5Util.MD5(user.getPassword()));
        user.setUserId(UuidUtil.getUUID());
        Date create_day = new Date();
        //入职时间
        user.setCreateTime(
                TimeUtils.formatDate(create_day)
        );
        user.setStatus(1);
        tUserMapper.insertUser(user);
    }

    @Override
    public boolean login(TUser user) {
        user.setPassword(Md5Util.MD5(user.getPassword()));
        TUser dbUser = tUserMapper.getUserByNameAndPwd(user.getName(), user.getPassword());
        if (dbUser != null) {
            return true;
        }
        return false;
    }

    @Override
    public String checkName(String name) {
        String username = null;
        username = tUserMapper.checkName(name);
        return username;
    }

    @Override
    public PageBean getUsers(int currentPage, int pageSize, String beginDate, String endDate) {
        int index = (currentPage - 1) * pageSize;
        List<TUser> userList = tUserMapper.getUsersList(index, pageSize, beginDate, endDate);
        int total = 0;
        total = tUserMapper.getUserCount(beginDate, endDate);
        PageBean obj = new PageBean();
        obj.setList(userList);
        obj.setTotal(total);
        return obj;
    }

    @Override
    public int updateUser(TUser user) {
        int i = 0;
        i = tUserMapper.updateUser(user);
        return i;
    }

    @Override
    public TUser getUserById(String userId) {
        TUser user = tUserMapper.getUserById(userId);
        return user;
    }

    @Override
    public Object getUserForm(String year, Integer departId) {
        year = TimeUtils.getDefaultDate(year);
        //计算一年每月的入职人数
        List<ExpenseForms> enterList = tUserMapper.getUserForm(TimeUtils.getMonths(year), departId, 1);
        List<ExpenseForms> leaveList = tUserMapper.getUserForm(TimeUtils.getMonths(year), departId, 0);
        enterList = FormUtils.checkYearForm(enterList, TimeUtils.getMonths(year));
        leaveList = FormUtils.checkYearForm(leaveList, TimeUtils.getMonths(year));
        ExpenseForms form = new ExpenseForms();
        form.setObj(new Object[2]);
        for(int i =0 ;i<enterList.size(); i++){
            form.getNowValeList().add(enterList.get(i).getValue());
            form.getLastValeList().add(leaveList.get(i).getValue()*(-1));
        }
        form.setNowTimeList(TimeUtils.getMonths(year));
        return form;
    }
}
