package com.wage.util;

import com.wage.bean.CommomFormBean;
import com.wage.bean.ExpenseForms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormUtils {


    /**
     * 适用于日期类型
     * 补全一年的数据，并根据时间排序排序
     * @param list
     * @param monthList
     * @return
     */
    public static List<ExpenseForms> checkYearForm(List<ExpenseForms> list, List<String> monthList){
        ExpenseForms expenseForms;
        List <String> copyMonthList = monthList;
        List<ExpenseForms> expenseList = new ArrayList<>();
        if(list ==null ||list.size()==0){

         //   list = new ArrayList<>();
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
        }else {
            return list;
        }
        Collections.sort(expenseList);
        monthList = copyMonthList;
        return expenseList;

    }

    /**
     * 使用int类型转为对应的表达式，如部门，行业。
     * @param list
     * @param typeList
     * @param typeName
     * @return
     */
    public static List<CommomFormBean> checkFormToCommBean (List <ExpenseForms> list , List<Integer> typeList,String typeName){
        List<CommomFormBean> commomList = new ArrayList<>();

        CommomFormBean commonBean ;
        //填充为空的数据
        if(list ==null && list.size()==0){
            for(Integer type : typeList){
                commonBean = new CommomFormBean();
                String name = FormUtils.getTypeName(type,typeName);
                commonBean.setName(name);
                commonBean.setValue((long) 0);
                commomList.add(commonBean);
            }
        }else if (list.size()>0 && list.size()<typeList.size()){
            for(int i =0;i<list.size();i++){
                if(typeList.contains(list.get(i).getIntType())){
                    commonBean = new CommomFormBean();
                    commonBean.setName(FormUtils.getTypeName(list.get(i).getIntType(),typeName));
                    commonBean.setValue(list.get(i).getValue());
                    commomList.add(commonBean);
                    typeList.remove(list.get(i).getIntType());
                }
            }
            for(Integer type : typeList){
                commonBean = new CommomFormBean();
                String name = FormUtils.getTypeName(type,typeName);
                commonBean.setName(name);
                commonBean.setValue((long) 0);
                commomList.add(commonBean);
            }
        }else {
            for(ExpenseForms obj : list){
                commonBean = new CommomFormBean();
                commonBean.setName(FormUtils.getTypeName(obj.getIntType(),typeName));
                commonBean.setValue(obj.getValue());
                commomList.add(commonBean);
            }
        }
        //ListUtils.sort(commomList,true,"value");
        return commomList;
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
    public static List<Long> getValueList(List<ExpenseForms> list){
        List<Long> valueList = new ArrayList<>();
        for(ExpenseForms obj : list){
            valueList.add(obj.getValue());
        }
        return  valueList;
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
            }
        }else if(name.equals("item_status")){
            switch (type){
                case 0:typeName="未进行";break;
                case 1:typeName="进行中";break;
                case 2:typeName="已完成";break;
            }
        }else if(name.equals("depart")){
            switch (type){
                case 0:typeName="研发部";break;
                case 1:typeName="财务部";break;
                case 2:typeName="人力资源部";break;
                case 3:typeName="采购部";break;
                case 4:typeName="事业部";break;
            }
        }else if(name.equals("expense_type")){
            switch (type){
                case 0:typeName="差旅";break;
                case 1:typeName="交通";break;
                case 2:typeName="日常";break;
                case 3:typeName="其他";break;
            }
        }
        return typeName;
    }
}
