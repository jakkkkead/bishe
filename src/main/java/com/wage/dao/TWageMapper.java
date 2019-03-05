package com.wage.dao;

import com.wage.bean.ExpenseForms;
import com.wage.bean.TWage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration;


import java.util.List;
@Mapper
public interface TWageMapper {
    public int insertWage(@Param("list") List<TWage> list);
    /**
     * 获取年工资，分成各个工资部分
     * @param year ：yyyy
     * @param departId
     * @return
     */
    public TWage getYearWageForm(@Param("year") String year,@Param("departId") Integer departId);

    /**
     * 获取每月的工资，有工资各部门组成
     * @param date :yyyy-MM-01
     * @param departId
     * @return
     */
    public TWage getWageForm (@Param("date")String date , @Param("departId")Integer departId);

    /**
     * 根据时间分组，获取每个月的总工资
     * @param monthList : yyyy-mm
     * @return
     */
    public List<ExpenseForms> getWageToMonth (@Param("monthList") List<String> monthList);

    /**
     * 根据部门分组，获取一年的总工资
     * @param year : yyyy
     * @return
     */
    public List<ExpenseForms> getWageToDepart(@Param("year") String year);

    /**
     * 查询某时间内的工资总和
     * @param beginTime
     * @param endTime
     * @return
     */
    public Long getTotalWage(@Param("beginTime")String beginTime,@Param("endTime")String endTime);

    /**
     * 联表查询，查出报销和工资总和,根据部门分组
     * @param year ： yyyy
     * @return
     */
    public List<ExpenseForms> getWageAndExpenseToDepart(@Param("year") String  year);

    /**
     * 联表查询，查出报销和工资总和，根据月份分组
     * @param monthList : yyyy-MM
     * @return
     */
    public List<ExpenseForms>getWageAndExpToMonth(@Param("monthList") List<String> monthList);
}
