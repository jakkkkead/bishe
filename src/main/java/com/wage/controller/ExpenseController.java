package com.wage.controller;

import com.wage.bean.*;
import com.wage.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @RequestMapping("addExpense")
    @ResponseBody
    public RestResult addExpense(TExpense expense){
        int i = expenseService.addExpense(expense);
        if(i>0){
            return RestResultGenerator.createOkResult();
        }else {
            return RestResultGenerator.createFailResult();
        }

    }
    @RequestMapping("getExpense")
    @ResponseBody
    public RestResult getExpense(int currentPage, int pageSize, String beginDate, String endDate){
        List<TExpense> list = expenseService.getExpense(beginDate,endDate,null,currentPage,pageSize);
        Integer count = expenseService.getExpenseCount(beginDate,endDate,null);
        PageBean object = new PageBean();
        object.setList(list);
        object.setTotal(count);
        return RestResultGenerator.createOkResult(object);
    }
    @RequestMapping("updateExpense")
    @ResponseBody
    public RestResult searchExpense(TExpense expense){
        int i = expenseService.updateExpese(expense);
        if(i>0){
            return RestResultGenerator.createOkResult();
        }else{
            return RestResultGenerator.createFailResult();
        }

    }
    @RequestMapping("checkExpense")
    @ResponseBody
    public RestResult checkExpense(String expenseId, int flag , HttpSession session){
       TUser user= (TUser) session.getAttribute("user");
       int i = expenseService.checkExpense(expenseId,flag,null);
       if (i>0){
           return RestResultGenerator.createOkResult();
       }
        return RestResultGenerator.createFailResult();
    }

    /**
     * 获取报销月报表
     * @param dayTime
     * @param departId
     * @return
     */
    @RequestMapping("getExpenseForms")
    @ResponseBody
    public RestResult getExpenseForms(String dayTime, Integer departId){
        if(departId !=null && departId ==5){
            departId =null;
        }
        ExpenseForms obj = expenseService.getExpenseForms(dayTime,departId);
        return RestResultGenerator.createOkResult(obj);
    }
    /**
     *  获取报销费用年报表
     * @param year
     * @param departId
     * @return
     */
    @RequestMapping("getYearExpenseForms")
    @ResponseBody
    public RestResult getYearExpenseForms(String year,Integer departId){
        if(departId !=null && departId ==5){
            departId =null;
        }
        ExpenseForms expenseForms = expenseService.getYearExpenseForms(year,departId);

        return RestResultGenerator.createOkResult(expenseForms);
    }
}
