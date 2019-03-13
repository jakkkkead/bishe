package com.wage.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.yun.comom.Test1;
@Service
public class Test1Impl implements Test1 {
    @Override
    public String say() {
        System.out.println("kdfj");
        return  "dfjk";
    }
}
