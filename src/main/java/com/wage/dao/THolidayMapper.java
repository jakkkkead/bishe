package com.wage.dao;

import com.wage.bean.THoliday;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface THolidayMapper {
      public int createHoliday(THoliday holiday);
      public List getHolidayList(@Param("index") int index,@Param("pageSize") int pageSize,@Param("beginDate") String beginDate, @Param("endDate")String endDate);
      public Integer getCount(@Param("beginDate") String beginDate, @Param("endDate")String endDate);
      public Integer checkHoliday(@Param("holidayId") String holidayId ,@Param("status") int status ,@Param("approveName") String approveName);
      public Integer updateHoliday(THoliday holiday);
      public List getHolidayForm(@Param("monthList")List<String> monthList ,@Param("departId")Integer departId ,@Param("type") Integer type);
}
