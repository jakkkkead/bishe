package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class THoliday {
    private String holidayId;
    private String name;
    private Integer departId;
/*
假期类型
 */
    private Integer type;

    private String beginTime;

    private String endTime;

    private Integer status;

    private String approveName;

    private String userId;
    private String remark;

}
