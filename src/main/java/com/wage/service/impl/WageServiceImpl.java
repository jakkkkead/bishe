package com.wage.service.impl;

import com.wage.bean.*;
import com.wage.dao.THolidayMapper;
import com.wage.dao.TUserMapper;
import com.wage.dao.TWageMapper;
import com.wage.service.WageService;
import com.wage.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WageServiceImpl implements WageService {
    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private THolidayMapper holidayMapper;
    @Autowired
    private TWageMapper wageMapper;

    @Override
    public void caculateWageByHand(String beginTime, String endTime) {
        //根据部门计算,获取yyyy-mm
        List<String> monthList = TimeUtils.getBetweenMonth(beginTime, endTime);
        System.out.println("工资开始计算");
        List<TWage> wageList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //先该获取部门的所有员工
            List<TUser> userList = userMapper.getUserInfo(i, 1);

            //按月计算每个人的工资
            for (String month : monthList) {


                for (TUser user : userList) {
                    TWage wage = new TWage();

                    List<THoliday> holidayList = holidayMapper.getHolidayWage(month, user.getName());
                    int totalHoliday = getTotalHoliday(holidayList, month);
                    //事假扣除
                    Double holidayPay = totalHoliday * 100.00;
                    //基本工资：5000*职位
                    Double baseWage = 5000.00 * (user.getPost() + 1);
                    //社保
                    Double socialPay = baseWage * 0.02;
                    //公积金
                    Double accumulationFund = baseWage * 0.1;
                    Double takePay = baseWage - socialPay - accumulationFund - holidayPay;
                    if (takePay < 0) {
                        takePay = 0.0;
                    }
                    wage.setName(user.getName());
                    wage.setBaseWage(baseWage);
                    wage.setSocialPay(socialPay);
                    wage.setHolidayPay(holidayPay);
                    wage.setAccumulationFund(accumulationFund);
                    wage.setTakePay(takePay);
                    wage.setHolidayDay(totalHoliday);
                    wage.setWorkDay(22 - totalHoliday);
                    wage.setDepartId(user.getDepartId());
                    wage.setWorkTime(month + "-01");
                    wageList.add(wage);
                }
            }

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int  y = wageMapper.insertWage(wageList);
                System.out.println(beginTime + "到" + endTime +y + "条工资数据以全部导入");
            }
        }).start();


    }

    @Override
    public Object getWageForm(String month, Integer departId) {
        //yyyy-MM-dd
        month = TimeUtils.getDefaultDate(month);
        TWage wage = wageMapper.getWageForm(month,departId);
        Map obj1 = getItemWage(wage);
        Map obj2 = getItemWage(wage);
        obj2.remove("基础工资");
        ExpenseForms form  = new ExpenseForms();
        form.setObj(new Object[2]);
        form.getObj()[0] = obj1;
        form.getObj()[1] = obj2;
        form.setDate(TimeUtils.getMonthOfDay(month));
        return form;
    }
    public Map getItemWage(TWage wage){
        if(wage == null){
            wage = new TWage();
        }
        Map<String,Double> map = new HashMap<>();
        map.put("事假扣除",wage.getHolidayPay());
        map.put("社保",wage.getSocialPay());
        map.put("公积金",wage.getAccumulationFund());
        map.put("基础工资",wage.getBaseWage());
        map.put("实发工资",wage.getTakePay());
        return  map;
    }


    public int getTotalHoliday(List<THoliday> list, String date) {
        String firstDay = date + "-01";
        String lastDay = TimeUtils.getMonthLastDay(firstDay);
        int total = 0;
        if (list.size() == 0 || list == null) {
            return 0;
        }
        for (THoliday holiday : list) {
            String hBegin = holiday.getBeginTime();
            String hEnd = holiday.getEndTime();
            if (hEnd.compareTo(lastDay) > 0 && hBegin.compareTo(firstDay) < 0) {
                total += 22;
            } else if (hEnd.compareTo(lastDay) > 0 && hBegin.compareTo(firstDay) >= 0) {
                total += TimeUtils.getBetweenDays(hBegin, lastDay).size();
            } else if (hEnd.compareTo(lastDay) <= 0 && hBegin.compareTo(firstDay) >= 0) {
                total += TimeUtils.getBetweenDays(hBegin, hEnd).size();
            }
        }
        if (total > 22) {
            total = 22;
        }
        return total;
    }
}
