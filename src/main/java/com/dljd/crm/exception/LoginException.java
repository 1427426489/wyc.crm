package com.dljd.crm.exception;

//自定义登录异常类
public class LoginException extends RuntimeException{

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
