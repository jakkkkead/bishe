package com.wage.service;

import com.wage.bean.PageBean;
import com.wage.bean.THoliday;

public interface HolidayService {
    int createHoliday(THoliday holiday);
    PageBean getHolidayList(int currentPage, int pageSize, String beginDate, String endDate);
    int checkHoliday (String holidayId,int flag , String name);
    int updateHoliday(THoliday holiday);
    public Object getHolidayForm(String year , Integer departId);
}
