package com.wyc.test;

import com.dljd.crm.services.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {

    @Autowired
    LoginService loginService;

    @Test
    public void test(){
        //业务方法调用了Http对象，只有在SpringMVC环境下才可调用该业务方法
        loginService.login("10001","12345");
    }

    @Test
    public void test01(){
        System.out.println(Math.round(-1.9));
    }
}
