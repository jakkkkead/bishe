package com.wage.service.impl;

import com.wage.bean.CommomFormBean;
import com.wage.bean.ExpenseForms;
import com.wage.bean.TItem;
import com.wage.dao.TItemMapper;
import com.wage.service.ItemService;
import com.wage.util.FormUtils;
import com.wage.util.ListUtils;
import com.wage.util.TimeUtils;
import com.wage.util.UuidUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private  TItemMapper itemMapper;

    @Override
    public int createItem(TItem item) {
        int i = 0;
        String id = UuidUtil.getUUID();
        item.setId(id);
        i = itemMapper.createItem(item);
        return i;
    }

    @Override
    public List seleceItem(String beginDate, String endDate, int currentPage, int pageSize, Integer departId) {
        if (departId == 5) {
            departId = null;
        }
        int index = (currentPage - 1) * pageSize;
        List<TItem> itemList = itemMapper.selectItem(beginDate, endDate, index, pageSize, departId);
        return itemList;
    }

    @Override
    public int getCount(String beginDate, String endDate, Integer departId) {
        if (departId == 5) {
            departId = null;
        }
        int total = 0;
        total = itemMapper.getCount(beginDate, endDate, departId);
        return total;
    }

    @Override
    public int updateItem(TItem item) {
        int i = itemMapper.updateItem(item);
        return i;
    }

    @Override
    public Object getItemForm(String year, Integer departId) {
        year = TimeUtils.getDefaultDate(year);
        //转化成字符串：yyyy
        String sdate = String.valueOf(TimeUtils.getYear(year));
        List<ExpenseForms> statusForms = itemMapper.getItemFormsToStatus(sdate, departId, null, "status");
        statusForms = checkItemForm(statusForms, FormUtils.getTypeList(3));
        List typeList = new ArrayList();
        List statusList;
        for (ExpenseForms expenseForms : statusForms) {
            List<ExpenseForms> typeForms = itemMapper.getItemFormsToType(sdate, departId, expenseForms.getIntType(), "type");
            typeForms = checkItemForm(typeForms, FormUtils.getTypeList(8));
            List<CommomFormBean> typeCommonList = getCommFormBean(typeForms, "item_type");
            typeList.addAll(typeCommonList);
            // expenseForms.setObjList(typeCommonList);
        }
        statusList = getCommFormBean(statusForms, "item_status");
        ExpenseForms returnForm = new ExpenseForms();
        returnForm.setObj(new Object[2]);
        returnForm.getObj()[0] = statusList;
        returnForm.getObj()[1] = typeList;
        returnForm.setDate(sdate + "年");
        return returnForm;
    }

    @Override
    public Object getItemMapForm(String year) {
        year = TimeUtils.getDefaultDate(year);
        //返回字符串格式 ： yyyy
        String yDate = String.valueOf(TimeUtils.getYear(year));
        List<CommomFormBean> mapList = itemMapper.getItemMapForm(yDate);
        ExpenseForms obj = new ExpenseForms();
        Object detail = getDetailMapForm(yDate,null);
        obj.setObj(new Object[1]);
        obj.getObj()[0] = detail;
        obj.setObjList(mapList);
        obj.setDate(yDate);
        return obj;
    }

    @Override
    public Object getDetailMapForm(String year, String area) {
        if(year.length() > 5){
            year = year.substring(0,4);
        }
        List<ExpenseForms> depForms = itemMapper.getDetailMapForm(year,area);
        depForms = checkItemForm(depForms,FormUtils.getTypeList(5));
        ExpenseForms reObj = new ExpenseForms();
        ListUtils.sort(depForms, true, "value");
        for(ExpenseForms obj : depForms){
            String name = FormUtils.getTypeName(obj.getIntType(),"depart");
            Long value =obj.getValue();
            reObj.getNowValeList().add(value);
            reObj.getNowTimeList().add(name);
        }
        List<ExpenseForms> typeForms = itemMapper.getDetailMaptoType(year,area);
        typeForms =checkItemForm(typeForms,FormUtils.getTypeList(6));
        List<CommomFormBean> typeCommomForm = getCommFormBean(typeForms,"item_type");
        reObj.getObjList().add(typeCommomForm);
        reObj.setDate(year);
        reObj.setArea(area);
        return reObj;
    }

    public List<CommomFormBean> getCommFormBean(List<ExpenseForms> list, String name) {
        CommomFormBean bean;
        List<CommomFormBean> beanList = new ArrayList<>();
        for (ExpenseForms obj : list) {
            bean = new CommomFormBean();
            bean.setValue(obj.getValue());
            bean.setName(FormUtils.getTypeName(obj.getIntType(), name));
            beanList.add(bean);
        }
        return beanList;
    }

    public List<ExpenseForms> checkItemForm(List<ExpenseForms> list, List<Integer> typeList) {
        List<ExpenseForms> defList;
        ExpenseForms obj;
        if (list == null || list.size() == 0) {
            defList = new ArrayList<>();

            for (int i = 0; i < typeList.size(); i++) {
                obj = new ExpenseForms();
                obj.setIntType(i);
                obj.setValue((long) 0);
                defList.add(obj);
            }
            ListUtils.sort(defList, true, "intType");
            return defList;
        } else if (list.size() > 0 && list.size() < typeList.size()) {
            for (ExpenseForms o : list) {
                if (typeList.contains(o.getIntType())) {
                    typeList.remove(o.getIntType());
                }
            }
            for (Integer type : typeList) {
                obj = new ExpenseForms();
                obj.setIntType(type);
                obj.setValue((long) 0);
                list.add(obj);
            }
            ListUtils.sort(list, true, "intType");

        }
        return list;
    }
}
