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

    /**
     * 构造Integer 类型的type
     * @param size
     * @return
     */
    public static List<Integer> getTypeList(int size){
        List<Integer> list = new ArrayList<>();
        for(int i =0 ; i< size;i++){
            list.add(i);
        }
        return list;
    }

    /**
     * 根据不同的业务名的标识，获取相应的业务名
     * @param type
     * @param name
     * @return
     */
    public static String getTypeName(int type,String name){
        String typeName ="";
        if(name.equals("item_type")){
            switch (type){
                case 0:typeName="餐饮";break;
                case 1:typeName="制造";break;
                case 2:typeName="纺织";break;
                case 3:typeName="互联网";break;
                case 4:typeName="金融";break;
                case 5:typeName="其他";break;
                case 6:typeName="气象";break;
                case 7:typeName="其他";break;
            }
        }else if(name.equals("item_status")){
            switch (type){
                case 0:typeName="未进行";break;
                case 1:typeName="进行中";break;
                case 2:typeName="已完成";break;
            }
        }
        return typeName;
    }
}
