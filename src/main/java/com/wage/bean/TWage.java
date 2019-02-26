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
    private Double baseWage;

    private Integer workDay;

    private Integer holidayDay;

    private String userId;
    /**
     * 社保扣除
     */
    private Double socialPay;
    /**
     * 公积金
     */

    private Double accumulationFund;
    /**
     * 实发工资
     */
    private Double takePay;

    private Integer status;

    private Date workTime;
    private Integer departId;

}
