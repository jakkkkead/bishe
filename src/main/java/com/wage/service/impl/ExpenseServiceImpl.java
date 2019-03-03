package com.wage.service.impl;

import com.wage.bean.ExpenseForms;
import com.wage.bean.TExpense;
import com.wage.dao.TExpenseMapper;
import com.wage.service.ExpenseService;
import com.wage.util.TimeUtils;
import com.wage.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private TExpenseMapper expenseMapper;

    @Override
    public int addExpense(TExpense expense) {
        expense.setExpenseId(UuidUtil.getUUID());
        expense.setStatus(1);
//        Date nowDay = new Date();
//        expense.setCreateTime(TimeUtils.formatter.get().format(nowDay));
        int i = expenseMapper.addExpense(expense);
        return i;
    }

    @Override
    public List getExpense(String beginTime, String endTime, String name, int currentPage, int pageSize) {
        int currentIndex = (currentPage - 1) * pageSize;
        List<TExpense> expenseList = expenseMapper.selectExpense(beginTime, endTime, name, currentIndex, pageSize);
        return expenseList;
    }

    @Override
    public Integer getExpenseCount(String beginTime, String endTime, String name) {
        Integer count = 0;
        count = expenseMapper.getExpensCount(beginTime, endTime, name);
        return count;
    }

    @Override
    public List searchExpense(String beginTime, String endTime, String name, int currentPage, int pageSize) {
        int currentIndex = (currentPage - 1) * pageSize;
        List<TExpense> expenseList = expenseMapper.selectExpense(beginTime, endTime, name, currentIndex, pageSize);
        return expenseList;
    }

    @Override
    public int updateExpese(TExpense expense) {
        int i = expenseMapper.updateExpense(expense);
        return i;
    }

    @Override
    public int checkExpense(String expenseId, int flag, String name) {
        int i = 0;
        TExpense expense = expenseMapper.getExpenseById(expenseId);
        if (expense == null) {
            return i;
        }
        expense.setStatus(flag);
        expense.setApproveName(name);
        i = expenseMapper.updateExpense(expense);
        return i;
    }

    @Override
    public ExpenseForms getExpenseForms(String dayTime, Integer departId) {
        String beginDate = null;
        String endDate = null;
        //默认计算当前月份数据
        if (dayTime == null || dayTime.equals("null") || dayTime.equals("")) {
            Date day = new Date();
            dayTime = TimeUtils.formatDate(day);
        }
        beginDate = TimeUtils.getMonthFirstDay(dayTime);
        endDate = TimeUtils.getMonthLastDay(dayTime);
        List<TExpense> expenseList = new ArrayList();
        expenseList = expenseMapper.getExpenseForms(beginDate, endDate, departId);
        //计算上月的报销总额
        Long lastMonthTotal = expenseMapper.getTotalExpense(TimeUtils.getAddMonth(beginDate,-1),TimeUtils.getAddMonth(endDate,-1),departId);
        checkForms(expenseList);
        ExpenseForms expenseForms = new ExpenseForms();
        expenseForms.setPieList(expenseList);
        expenseForms.setDate(dayTime.substring(0, 7));
        expenseForms.setValue(lastMonthTotal);
        return expenseForms;
    }

    @Override
    public ExpenseForms getYearExpenseForms(String year, Integer departId) {
        Date yearDate = new Date();
        if (year == null || year.equals("null") || year.equals("")) {
            year = TimeUtils.formatDate(yearDate);
        }
        List nowMonthList = getMonths(year);
        List lastMonthList = getMonths(TimeUtils.addMonth(year, -12));
        List<ExpenseForms> nowForms = expenseMapper.getYearExpenseForms(nowMonthList, departId);
        List<ExpenseForms> lastForms = expenseMapper.getYearExpenseForms(lastMonthList, departId);
        nowForms = checkYearForm(nowForms, nowMonthList);
        lastForms = checkYearForm(lastForms, lastMonthList);
        Collections.sort(nowForms);
        Collections.sort(lastForms);
        ExpenseForms totalForms = new ExpenseForms();
        for (int i = 0; i < nowForms.size(); i++) {
            totalForms.getNowValeList().add(nowForms.get(i).getMoney());

            totalForms.getLastValeList().add(lastForms.get(i).getMoney());
            //  totalForms.getNowTimeList().add(nowForms.get(i).getDateType());
            //   totalForms.getLastTimeList().add(lastForms.get(i).getDateType());
        }
        totalForms.setDate(TimeUtils.getYear(year) + "年");
        //计算饼图数据，根据类型划分
        List<TExpense> expenseList = new ArrayList();
        expenseList = expenseMapper.getExpenseForms(TimeUtils.getYearFirstDay(year), TimeUtils.getLastDayOfYear(year), departId);
        checkForms(expenseList);
        totalForms.setPieList(expenseList);
        return totalForms;
    }

    public List<ExpenseForms> checkYearForm(List<ExpenseForms> list, List<String> monthList) {
        ExpenseForms expenseForms;
        List<ExpenseForms> expenseList = new ArrayList<>();
        if (list == null || list.size() == 0) {

            list = new ArrayList<>();
            for (String month : monthList) {
                expenseForms = new ExpenseForms();
                expenseForms.setMoney((long) 0);
                expenseForms.setDateType(month);
                expenseList.add(expenseForms);
            }
            return expenseList;
        } else if (list.size() > 0 && list.size() < 12) {
            for (int i = 0; i < list.size(); i++) {
                if (monthList.contains(list.get(i).getDateType())) {
                    monthList.remove(list.get(i).getDateType());
                }
            }

            for (String month : monthList) {
                expenseForms = new ExpenseForms();
                expenseForms.setDateType(month);
                expenseForms.setMoney((long) 0);
                list.add(expenseForms);
            }
            expenseList = list;
            return expenseList;
        }
        return list;

    }

    /**
     * 得到一年当中的12个月 ： yyyy-MM
     *
     * @param date
     * @return
     */
    public List<String> getMonths(String date) {
        List<String> list = new ArrayList<>();
        String firstDay = TimeUtils.getYearFirstDay(date);
        String firstMonth = TimeUtils.getMonthOfDay(firstDay);
        list.add(firstMonth);
        for (int i = 0; i < 11; i++) {
            //   String day = list.get(i);
            String day = TimeUtils.addMonth(firstDay, i + 1);
            list.add(TimeUtils.getMonthOfDay(day));
        }
        return list;
    }

    public void checkForms(List<TExpense> list) {
        List typeList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (TExpense expense : list) {
                int type = expense.getType();
                checkTypeName(type, expense);
                typeList.add(type);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                TExpense expense = new TExpense();
                checkTypeName(i, expense);
                expense.setMoney((long) 0);
                list.add(expense);
            }
        }
        if (typeList.size() > 0) {
            TExpense obj = new TExpense();
            if (!typeList.contains(0)) {
                obj.setTypeName("差旅");
                obj.setMoney((long) 0);
                list.add(obj);
            }
            if (!typeList.contains(1)) {
                obj.setTypeName("交通费");
                obj.setMoney((long) 0);
                list.add(obj);
            }
            if (!typeList.contains(2)) {
                obj.setTypeName("日常费用");
                obj.setMoney((long) 0);
                list.add(obj);
            }
            if (!typeList.contains(3)) {
                obj.setTypeName("广告费用");
                obj.setMoney((long) 0);
                list.add(obj);
            }
            if (!typeList.contains(4)) {
                TExpense tExpense = new TExpense();
                tExpense.setTypeName("其他");
                tExpense.setMoney((long) 0);
                list.add(tExpense);
            }
        }

    }

    public void checkTypeName(int type, TExpense expense) {
        switch (type) {
            case 0:
                expense.setTypeName("差旅");
                break;
            case 1:
                expense.setTypeName("交通费");
                break;
            case 2:
                expense.setTypeName("日常费用");
                break;
            case 3:
                expense.setTypeName("广告费用");
                break;
            case 4:
                expense.setTypeName("其他费用");
                break;
        }
    }
}
