package com.wage.demo;


import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

public class Test extends WagesApplicationTests {
//
//    @org.junit.Test
//    public void test1(){
//        double a =32933.3232;
//        double b =324.233;
//        BigDecimal bd = new BigDecimal(a/b);
//        double res= bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//        System.out.println(res);
//    }
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @org.junit.Test
    public void test2(){
        String key = "key1";
        redisTemplate.opsForValue().set(key,"gunajia");
        System.out.println(redisTemplate.opsForValue().get(key));
    }
}
