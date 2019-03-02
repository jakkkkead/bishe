package com.wage.service;

public interface WageService {
    public void caculateWageByHand(String beginTime,String endTime);

    public Object getWageForm(String month,Integer departId);
}
