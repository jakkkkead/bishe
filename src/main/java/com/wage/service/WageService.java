package com.wage.service;

public interface WageService {
    public void caculateWageByHand(String beginTime,String endTime);

    public Object getWageForm(String month,Integer departId);

    /**
     * 查询年度工资情况，有各部分组成
     * @param year ：yyyy
     * @param departId
     * @return  ：map
     */
    public Object getYearWageForm(String year,Integer departId);
}
