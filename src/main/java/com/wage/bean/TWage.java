package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TWage {
    private String name;
    /**
     * 基本工资
     */
    private Double baseWage= 0.0;

    private Integer workDay =22;

    private Integer holidayDay =0;

    private String userId;
    /**
     * 社保扣除
     */
    private Double socialPay = 0.0;
    /**
     * 公积金
     */

    private Double accumulationFund = 0.0;
    /**
     * 实发工资
     */
    private Double takePay = 0.0;
    private Double holidayPay = 0.0;

    private Integer status =1;

    private String workTime;
    private Integer departId;

}
