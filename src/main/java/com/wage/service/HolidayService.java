package com.wage.service;

import com.wage.bean.PageBean;
import com.wage.bean.THoliday;

public interface HolidayService {
    int createHoliday(THoliday holiday);
    PageBean getHolidayList(int currentPage, int pageSize, String beginDate, String endDate,Integer departId);
    int checkHoliday (String holidayId,int flag , String name);
    int updateHoliday(THoliday holiday);

    /**
     * 获取假期报表数据
     * @param year
     * @param departId
     * @return
     */
    public Object getHolidayForm(String year , Integer departId);
}
