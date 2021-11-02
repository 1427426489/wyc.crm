package com.dljd.crm.controller;

import com.dljd.crm.beans.User;
import com.dljd.crm.services.LoginService;
import com.dljd.crm.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> loginDo(@RequestParam Map<String,String> map,HttpSession session,HttpServletResponse response){
        User user = loginService.login(map.get("username"), map.get("password"));
        //将查询到的用户数据存储到session对象中
        session.setAttribute("user",user);
        //判断用户是否勾选了十天免登陆
        if (map.get("autoLogin")!=null){
            //将数据存储到cookie对象中
            Cookie cookie1 = new Cookie("username",user.getLoginAct());
            cookie1.setMaxAge(10*24*60*60);
            //设置cookie对象的作用域为整个模块下
            cookie1.setPath("/");
            Cookie cookie2 = new Cookie("password",user.getLoginPwd());
            cookie2.setMaxAge(10*24*60*60);
            cookie2.setPath("/");
            //将cookie对象存储到响应对象中
            response.addCookie(cookie1);
            response.addCookie(cookie2);
        }
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/logout.do")
    public String logoutDo(HttpServletResponse response,HttpSession session){
        //退出登录
        //将session对象中的数据全部清空
        session.invalidate();//底层通过迭代器遍历数据并删除

        //如果用户设置了免登陆，还需要清空cookie数据
        //将键设置重名，值设为空，保存时长设为0，覆盖的同时清除cookie对象
        Cookie cookie1 = new Cookie("username",null);
        //作用域设为/ 可以让项目中的所有请求都携带该cookie对象
        cookie1.setPath("/");
        cookie1.setMaxAge(0);
        Cookie cookie2 = new Cookie("password",null);
        cookie2.setPath("/");
        cookie2.setMaxAge(0);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "redirect:/";
    }

    @RequestMapping(value = "/getAllUserName.json",method = RequestMethod.GET)
    @ResponseBody
    public Object getAllUsername(){
        return loginService.getAllUsername();
    }
}
