package com.wage.util;

import com.wage.bean.ExpenseForms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormUtils {
    /**
     * 补全一年的数据，并排序
     * @param list
     * @param monthList
     * @return
     */
    public static List<ExpenseForms> checkYearForm(List<ExpenseForms> list, List<String> monthList){
        ExpenseForms expenseForms;
        List <String> copyMonthList = monthList;
        List<ExpenseForms> expenseList = new ArrayList<>();
        if(list ==null ||list.size()==0){

            list = new ArrayList<>();
            for(String month : monthList){
                expenseForms = new ExpenseForms();
                expenseForms.setMoney((long)0);
                expenseForms.setDateType(month);
                expenseList.add(expenseForms);
            }
        }else if( list.size()>0 && list.size()<12){
            for(int i =0;i<list.size();i++){
                if(monthList.contains(list.get(i).getDateType())){
                    monthList.remove(list.get(i).getDateType());
                }
            }

            for(String month : monthList){
                expenseForms = new ExpenseForms();
                expenseForms.setDateType(month);
                expenseForms.setMoney((long)0);
                list.add(expenseForms);
            }
            expenseList = list;
        }
        Collections.sort(expenseList);
        monthList = copyMonthList;
        return expenseList;

    }
}
