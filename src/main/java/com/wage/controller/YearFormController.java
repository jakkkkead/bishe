package com.wage.controller;

import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.service.YearFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YearFormController {
    @Autowired
    private YearFormService yearFormService;
    @RequestMapping("getYearWageForm")
    public RestResult getYearWageForm(String year){
        Object obj = yearFormService.getYearForm(year);
        return RestResultGenerator.createOkResult(obj);
    }
    @RequestMapping("getYearDetailForm")
    public RestResult getYearDetailForm(String year){
        Object obj = yearFormService.getYearDetailForm(year);
        return RestResultGenerator.createOkResult(obj);
    }
}
