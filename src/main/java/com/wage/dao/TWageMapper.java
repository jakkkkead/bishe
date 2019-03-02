package com.wage.dao;

import com.wage.bean.TWage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration;


import java.util.List;
@Mapper
public interface TWageMapper {
    public int insertWage(@Param("list") List<TWage> list);
    public TWage getWageForm (@Param("date")String date , @Param("departId")Integer departId);
}
