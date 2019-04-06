package com.wage.controller;

import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.service.WageService;
import com.wage.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    //每月一号，自动计算上月工资
    @Scheduled( cron ="0 0 0 1 * ?")
    public RestResult AotuCaculateWage(){
        String date = TimeUtils.formatDate(new Date());
        //计算上月一号日期
        String lastMonthFirstDay = TimeUtils.getAddMonth(date,-1);
        //计算上月最后一天
        String lastMonthEndDay = TimeUtils.getAddDate(date,-1);
        try{
            caculateWageByHand(lastMonthFirstDay,lastMonthEndDay);
            return RestResultGenerator.createOkResult("工资自动计算成功");
        }catch (Exception e){
            return RestResultGenerator.createFailResult("工资自动计算失败");
        }


    }
    @RequestMapping("getWageForm")
    public RestResult getWageForm(String month,Integer departId){
        if (departId != null && departId == 5) {
            departId = null;
        }
        Object obj = wageService.getWageForm(month,departId);
        return RestResultGenerator.createOkResult(obj);
    }
}
