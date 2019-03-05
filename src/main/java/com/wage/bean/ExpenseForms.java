package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseForms implements Comparable<ExpenseForms>{
    private Long money = (long)0;
    private String type;
    private String dateType;
    private String area;
    private Integer intType;
    private Long value = (long)0;
    private List<String> lastTimeList = new ArrayList<>();
    private List<String> nowTimeList = new ArrayList<>();
    private List<Long> nowValeList = new ArrayList<>();
    private List<Long> lastValeList = new ArrayList<>();
    private List<TExpense> pieList = new ArrayList<>();
    private Object[] obj;
    private String date;
    private List objList = new ArrayList();

    @Override
    public int compareTo(ExpenseForms o) {
        int i = this.getDateType().compareTo(o.getDateType());
        return i;
    }
}
