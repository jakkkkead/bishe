package com.wage.service.impl;

import com.wage.bean.CommomFormBean;
import com.wage.bean.ExpenseForms;
import com.wage.bean.IncreateBean;
import com.wage.dao.TExpenseMapper;
import com.wage.dao.TItemMapper;
import com.wage.dao.TWageMapper;
import com.wage.service.WageService;
import com.wage.service.YearFormService;
import com.wage.util.FormUtils;
import com.wage.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class YearFormServiceImpl implements YearFormService {
    @Autowired
    private TItemMapper itemMapper;
    @Autowired
    private TWageMapper wageMapper;
    @Autowired
    private WageService wageService;
    @Autowired
    private TExpenseMapper expenseMapper;
    @Override
    public Object getYearForm(String year) {
        year = TimeUtils.getDefaultDate(year);
        String sYear =String.valueOf(TimeUtils.getYear(year));
        //月份列表：yyyy-mm
        List<String> monthList = TimeUtils.getMonths(year);
        //项目收入：根据月分组 ,收入：项目
        List <ExpenseForms> itemMonthList = itemMapper.getMonthItemForm(monthList);
        itemMonthList = FormUtils.checkYearForm(itemMonthList,monthList);
        List <ExpenseForms> itemDepartList = itemMapper.getYearItemFormToDepart(sYear);
        //转成：valeu，name 形式--》适配饼图
        List<CommomFormBean> itemDapartComList = FormUtils.checkFormToCommBean(itemDepartList,FormUtils.getTypeList(5),"depart");
        //支出 : 工资 + 报销
        List<ExpenseForms> payDepList = wageMapper.getWageAndExpenseToDepart(sYear);
        List<ExpenseForms> payMonthList = wageMapper.getWageAndExpToMonth(TimeUtils.getMonths(year));
        payMonthList = FormUtils.checkYearForm(payMonthList,TimeUtils.getMonths(year));
        List<CommomFormBean> payDepartComList = FormUtils.checkFormToCommBean(payDepList,FormUtils.getTypeList(5),"depart");

        Object obj[] = new Object[6];
        obj[0] = FormUtils.getValueList(itemMonthList);
        obj[1] = itemDapartComList;
        obj[2] = FormUtils.getValueList(payMonthList);
        obj[3] = payDepartComList;
        obj[4] = TimeUtils.getMonths(year);
        obj[5] = sYear;
        return obj;
    }

    @Override
    public Object getYearDetailForm(String year) {
        year = TimeUtils.getDefaultDate(year);
        String lastDate = TimeUtils.getAddMonth(year,-12);
        String beginTime = TimeUtils.getFirstDayOfYear(year);
        String endTime = TimeUtils.getLastDayOfYear(year);
        //字符串格式：yyyy
        String sYear = String.valueOf(TimeUtils.getYear(year));
        List<Long> nowPayList = getPayList(beginTime,endTime);
        List<Long> lastPayList = getPayList(TimeUtils.getFirstDayOfYear(lastDate),TimeUtils.getLastDayOfYear(lastDate));
        ExpenseForms obj = new ExpenseForms();
        obj.setNowValeList(nowPayList);
        obj.setLastValeList(lastPayList);
        obj.setDate(sYear);
        obj.setObjList(getIncreateList(nowPayList,lastPayList));
        //计算报销费用，根据类型分组
        List<ExpenseForms> nowTypeExpList = expenseMapper.getYearExpenseToType(sYear);
        List <CommomFormBean> expTypeCom = FormUtils.checkFormToCommBean(nowTypeExpList,FormUtils.getTypeList(4),"expense_type");
        //计算年工资组成
        Object wageMap = wageService.getYearWageForm(sYear,null);
        List<ExpenseForms> itemTypeList = itemMapper.getDetailMaptoType(sYear,null);
        List <CommomFormBean> itemTypeCom = FormUtils.checkFormToCommBean(itemTypeList,FormUtils.getTypeList(6),"item_type");
        obj.setObj(new Object[3]);
        obj.getObj()[0] =expTypeCom;
        obj.getObj()[1] =wageMap;
        obj.getObj()[2] =itemTypeCom;
        return obj;
    }
    public List<String> getIncreateList(List<Long> nowList , List<Long> lastList){
        DecimalFormat df = new DecimalFormat("#0.00");
        List<String> list = new ArrayList<>();
        list.add("同比增长率");
        for(int i =0 ;i<nowList.size();i++){
            String value = "0.00%";
            if(lastList.get(i) != 0){
              Double inc =  (nowList.get(i)-lastList.get(i))/100.00;
              value = df.format(inc)+"%";
            }
            list.add(value);
        }
        return list;
    }

    /**
     * 查询总工资，报销，项目及利润
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Long> getPayList(String beginTime,String endTime){
        //获取总工资
        Long totalWage = wageMapper.getTotalWage(beginTime,endTime);
        //获取总报销费用
        Long totalExp = expenseMapper.getTotalExpense(beginTime,endTime,null);
        //获取总项目收入
        Long totalItem = itemMapper.getTotalItemPay(beginTime,endTime);
        Long total = totalItem-totalExp-totalWage;
        List list = new ArrayList();
        list.add(totalWage);
        list.add(totalExp);
        list.add(totalItem);
        list.add(total);
        return  list;

    }

}
