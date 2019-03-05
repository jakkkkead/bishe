package com.wage.service;

import com.wage.bean.TItem;

import java.util.List;

public interface ItemService {
    public int createItem(TItem item);
    public List seleceItem(String beginDate,String endDate,int currentPage,int pageSize,Integer departId);
    public int getCount(String beginDate,String endDate,Integer departId);
    public int updateItem(TItem item);
    public Object getItemForm(String year,Integer departId);
    public Object getItemMapForm(String year);

    public Object getDetailMapForm(String year,String area);
}
