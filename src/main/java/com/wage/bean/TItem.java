package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TItem {
    private String id;
    private String area;
    private String name;

    private String beginTime;

    private String endTime;

    private Integer departId;

    private String principal;

    private String description;

    private Integer status;

    private Long payment;

    private Integer type;

    private Integer payType;


}
