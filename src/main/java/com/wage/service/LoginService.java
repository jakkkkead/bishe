package com.wage.service;

import com.wage.bean.PageBean;
import com.wage.bean.TUser;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.List;

public interface LoginService {
    /**
     * 注册用户
     * @param user
     */
    public void sigin(TUser user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    public boolean login(TUser user);

    /**
     * 检查用户名是否重复
     * @param name
     * @return
     */
    public String checkName(String name);

    /**
     * 获取用户信息
     * @param currentPage
     * @param pageSize
     * @param beginDate
     * @param endDate
     * @return
     */
    public PageBean getUsers(int currentPage, int pageSize, String beginDate, String endDate);

    public int updateUser(TUser user);
    public TUser getUserById(String userId);
}
