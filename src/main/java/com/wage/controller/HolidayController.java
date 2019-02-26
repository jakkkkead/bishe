package com.wage.controller;

import com.wage.bean.*;
import com.wage.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @RequestMapping("createHoliday")
    public RestResult createHoliday(THoliday holiday) {
        int i = holidayService.createHoliday(holiday);
        if (i > 0) {
            return RestResultGenerator.createOkResult();

        }
        return RestResultGenerator.createFailResult();
    }

    @RequestMapping("getHolidayList")
    public RestResult getHolidayList(int currentPage, int pageSize, String beginDate, String endDate) {
        PageBean pageBean = holidayService.getHolidayList(currentPage, pageSize, beginDate, endDate);

        return RestResultGenerator.createOkResult(pageBean);
    }

    @RequestMapping("checkHoliday")
    public RestResult checkHoliday(String holidayId, int flag, HttpSession session) {
        TUser user = (TUser) session.getAttribute("user");
        int i = holidayService.checkHoliday(holidayId, flag, null);
        if (i > 0) {
            return RestResultGenerator.createOkResult();
        } else {
            return RestResultGenerator.createFailResult();
        }
    }

    @RequestMapping("updateHoliday")
    public RestResult updateHoliday(THoliday holiday) {

        int i = holidayService.updateHoliday(holiday);
        if (i > 0) {
            return RestResultGenerator.createOkResult();
        } else {
            return RestResultGenerator.createFailResult();
        }
    }

    @RequestMapping("getHolidayForms")
    public RestResult getHolidayForms(String year, Integer departId) {
        if(departId !=null && departId ==5){
            departId =null;
        }
        ExpenseForms forms = (ExpenseForms) holidayService.getHolidayForm(year, departId);
        return RestResultGenerator.createOkResult(forms);
    }

}
