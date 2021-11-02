package com.dljd.crm.controller.interceptor;

import com.dljd.crm.beans.User;
import com.dljd.crm.exception.LoginException;
import com.dljd.crm.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断除登录请求外的所有请求是否已经登录过或者设置了免登陆
        HttpSession session = request.getSession();
        if (session==null){
            throw new LoginException("数据异常，请重新登录！！！");
        }
        if (session.getAttribute("user") == null) {
            String loginAct = null;
            String loginPwd = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    loginAct = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    loginPwd = cookie.getValue();
                }
            }
            //用户设置了免登陆,为session对象添加用户信息,继续执行请求对应的处理器方法
            if (loginAct != null && loginPwd != null) {
                //拦截器会拦截除/和/login.do的请求，执行login方法时可能会抛出LoginException异常,会由异常处理器接收进行处理
                //如果是同步请求，需要返回一个页面
                //如果是异步请求，需要返回json数据
                User user = loginService.login(loginAct, loginPwd);
                session.setAttribute("user",user);
                return true;
            }
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
