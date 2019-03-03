package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncreateBean {
    private String name;
    private String itemInc;
    private String expenseInc;
    private String totalInc;
}
