package com.dljd.crm.mapper;

import com.dljd.crm.beans.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {
    //根据用户名和密码查询用户信息
    User get(@Param("username") String username, @Param("password") String password);

    List<String> getAllUsername();
}
