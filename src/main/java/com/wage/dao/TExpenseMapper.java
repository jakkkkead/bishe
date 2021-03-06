package com.wage.dao;

import com.wage.bean.TExpense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
@Mapper
public interface TExpenseMapper {
    /**
     * 获取每年报销总和，根据类型分组
     * @param year
     * @return
     */
    public List getYearExpenseToType(@Param("year") String year);
    /**
     * 查询某时间段内的报销总和
     * @param beginTime
     * @param endTime
     * @return
     */
    public Long getTotalExpense(@Param("beginTime") String beginTime ,@Param("endTime") String endTime,@Param("departId") Integer departId);

    public int addExpense(TExpense expense);

    /**
     * 分页查询费用报销数据
     * @param beginTime
     * @param endTime
     * @param name
     * @param currentIndex
     * @param pageSize
     * @return
     */
    public List selectExpense(@Param("beginTime") String beginTime , @Param("endTime")String endTime,@Param("name") String name, @Param("currentIndex")int currentIndex, @Param("pageSize")int pageSize,@Param("departId") Integer departId);

    /**
     * 获取费用数据总条目数
     * @param beginTime
     * @param endTime
     * @param name
     * @return
     */
    public Integer getExpensCount(@Param("beginTime")String beginTime ,@Param("endTime")String endTime,@Param("name")String name,@Param("departId")Integer departId);
    public List searchExpense(@Param("beginTime") String beginTime , @Param("endTime")String endTime,@Param("name") String name, @Param("currentIndex")int currentIndex, @Param("pageSize")int pageSize);
    public int updateExpense(TExpense expense);
    public TExpense getExpenseById(@Param("expenseId") String expenseId);

    /**
     *获取部门在某个时间段内的数据
     * @param beginDate
     * @param departId
     * @return
     */
    public List getExpenseForms(@Param("beginDate") String beginDate,@Param("endDate") String endDate,@Param("departId") Integer departId);
    public List getYearExpenseForms(@Param("monthList") List<String> month,@Param("departId") Integer departId);

}
