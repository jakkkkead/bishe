package com.wage.demo;

import java.math.BigDecimal;

public class Test extends WagesApplicationTests {

    @org.junit.Test
    public void test1(){
        double a =32933.3232;
        double b =324.233;
        BigDecimal bd = new BigDecimal(a/b);
        double res= bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(res);
    }
}
