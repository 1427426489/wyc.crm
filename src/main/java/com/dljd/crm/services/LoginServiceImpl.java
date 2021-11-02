package com.dljd.crm.services;

import com.dljd.crm.beans.User;
import com.dljd.crm.exception.LoginException;
import com.dljd.crm.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 业务方法也可以通过依赖注入获取Http对象：
     *      1.使用该对象的前提是控制器调用了该业务方法
     *      2.控制器会自动传递http对象给业务层
     *      3.测试的时候必须具备SpringMVC环境才可测试该业务方法
     */
    @Autowired
    private HttpServletRequest request;

    @Override
    public User login(String username, String password) {
        User user = loginMapper.get(username, password);
        if (user == null){
            //通过全局的异常处理器给予业务方法处理结果的行为
            //使得控制器接收到的结果一定是正确的
            throw new LoginException("账号或密码错误！！！");
        }
        //判断用户账号是否已到过期时间
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime expireTime = LocalDateTime.parse(user.getExpireTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (nowTime.isAfter(expireTime)){
            throw new LoginException("账号已过期！！！");
        }
        //判断账号是否处于锁定状态
        if (user.getLockStatus().equals("0")){
            throw new LoginException("账号已锁定！！！");
        }
        //判断登录的IP地址是否在可登录的IP地址范围内
        //获取发出请求的浏览器的IP地址
        String remoteAddr = request.getRemoteAddr();
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(remoteAddr)){
            throw new LoginException("IP地址不符！！！");
        }
        return user;
    }

    @Override
    public List<String> getAllUsername() {
        return loginMapper.getAllUsername();
    }
}
