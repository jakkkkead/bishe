package com.wage.service.impl;

import com.wage.bean.ExpenseForms;
import com.wage.bean.PageBean;
import com.wage.bean.THoliday;
import com.wage.dao.THolidayMapper;
import com.wage.service.HolidayService;
import com.wage.util.FormUtils;
import com.wage.util.TimeUtils;
import com.wage.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.ThreadInfo;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private THolidayMapper holidayMapper;

    @Override
    public int createHoliday(THoliday holiday) {
        int i = 0;
        String holidayId = UuidUtil.getUUID();
        holiday.setHolidayId(holidayId);
        holiday.setStatus(0);
        i = holidayMapper.createHoliday(holiday);
        return i;
    }

    @Override
    public PageBean getHolidayList(int currentPage, int pageSize, String beginDate, String endDate) {
        int index = (currentPage - 1) * pageSize;
        List<THoliday> holidayList = holidayMapper.getHolidayList(index, pageSize, beginDate, endDate);
        int total = holidayMapper.getCount(beginDate, endDate);
        PageBean pageBean = new PageBean();
        pageBean.setList(holidayList);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public int checkHoliday(String holidayId, int flag, String name) {
        int i = 0;
        if (holidayId == null) {
            return i;
        }
        i = holidayMapper.checkHoliday(holidayId, flag, name);
        return i;
    }

    @Override
    public int updateHoliday(THoliday holiday) {
        int i = holidayMapper.updateHoliday(holiday);
        return i;
    }

    @Override
    public Object getHolidayForm(String year, Integer departId) {
        year = TimeUtils.getDefaultDate(year);
        ExpenseForms forms = new ExpenseForms();
        //初始化数组
        forms.setObj(new Object[5]);
        forms.setNowTimeList(TimeUtils.getMonths(year));
        for (int i = 0; i < 5; i++) {
            List<ExpenseForms> list = holidayMapper.getHolidayForm(TimeUtils.getMonths(year), departId, i);
            list = FormUtils.checkYearForm(list, TimeUtils.getMonths(year));
            forms.getObj()[i] = list;
        }

        return forms;
    }
}
