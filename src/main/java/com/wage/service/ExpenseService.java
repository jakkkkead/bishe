package com.wage.service;

import com.wage.bean.ExpenseForms;
import com.wage.bean.TExpense;

import java.util.List;

public interface ExpenseService {
    public int addExpense(TExpense expense);

    /**
     * 分页查询费用报销
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List getExpense(String beginTime ,String endTime,String name, int currentPage,int pageSize,Integer departId);

    /**
     * 获取总记录数
     * @param beginTime
     * @param endTime
     * @param name
     * @return
     */
    public Integer getExpenseCount(String beginTime ,String endTime,String name,Integer departId);
    public List searchExpense(String beginTime ,String endTime,String name, int currentPage,int pageSize);

    /**
     * 更新
     * @param expense
     * @return
     */
    public int updateExpese(TExpense expense);
    public int checkExpense(String expenseId,int flag , String name);
    public ExpenseForms getExpenseForms(String dayTime,Integer departId);
    public ExpenseForms getYearExpenseForms(String year, Integer departId);
}
