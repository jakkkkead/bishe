package com.wage.dao;

import com.wage.bean.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TUserMapper {
     public void insertUser(TUser user);
     public TUser getUserByNameAndPwd(@Param("name") String name ,@Param("password") String password);
     public String checkName(@Param("name") String name);
     public List getUsersList(@Param("index")int index,@Param("pageSize") int pageSize, @Param("beginDate")String beginDate,@Param("endDate") String endDate);
     public Integer getUserCount(@Param("beginDate")String beginDate,@Param("endDate") String endDate);
     public Integer updateUser(TUser user);
     public TUser getUserById(@Param("id")String id);
}
