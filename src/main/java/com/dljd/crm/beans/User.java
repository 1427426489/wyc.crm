package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;
    private String deptId;
    private String loginAct;
    private String name;
    private String loginPwd;
    private String email;
    //账号失效时间
    private String expireTime;
    //账号锁定状态，0锁定，1开放
    private String lockStatus;
    //允许登陆的IP地址
    private String allowIps;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;

}
