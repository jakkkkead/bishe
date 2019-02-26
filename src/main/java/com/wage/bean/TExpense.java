package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TExpense {
    private String expenseId;

    private String createName;

    private Integer departId;

    private Integer payway;

    private String receiverName;

    private String accountNumber;

    private String beginTime;

    private String endTime;

    private Integer type;

    private String remark;

    private Long money;

    private String createTime;

    private Integer status;

    private String approveName;
    private String typeName;

}
