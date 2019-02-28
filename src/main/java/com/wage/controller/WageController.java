package com.wage.controller;

import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.service.WageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WageController {
    @Autowired
    private WageService wageService;

    /**
     * 手动插入工资
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("cacuWage")
    public RestResult caculateWageByHand(String beginTime,String endTime){
        try {
            wageService.caculateWageByHand(beginTime,endTime);
            return RestResultGenerator.createOkResult();
        }catch (Exception e){
            return RestResultGenerator.createFailResult();
        }

    }
}
