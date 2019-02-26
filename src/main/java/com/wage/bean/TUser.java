package com.wage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUser {
    private String userId;

    private String name;

    private Integer sex;

    private String phone;

    private String email;

    private Integer departId;

    private String createTime;

    private String leaveTime;

    private Integer status;

    private String password;

    private String birthdate;

    private Integer post;

}
