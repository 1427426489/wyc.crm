package com.dljd.crm.controller.advice;

import com.dljd.crm.exception.CRUDException;
import com.dljd.crm.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//异常处理注解需要被spring容器扫描到才可用
@ControllerAdvice
public class AllControllerAdvice {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    //处理登录异常的异常处理器方法
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Map<String, Object> loginException(Exception e) throws IOException {
        //当拦截器中发生LoginException异常时，该方法应该根据拦截器拦截的请求做出相应处理
        //如果是同步请求，需要返回一个页面
        //如果是异步请求，需要返回json数据
        String xrw = request.getHeader("X-Requested-With");
        //同步请求的处理
        if (xrw == null) {
            //重定向到登录页面
            response.sendRedirect("/");
        }
        //异步请求的处理
        Map<String, Object> map = new HashMap<>();
        map.put("msg", e.getMessage());
        return map;
    }

    @ExceptionHandler(CRUDException.class)
    @ResponseBody
    public Map<String,Object> CRUDException(Exception e) throws ServletException, IOException {
        String xrw = request.getHeader("X-Requested-With");
        //判断发生异常的请求是同步请求还是异步请求
        if (xrw==null){
            //请求转发到err页面
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/err.jsp").forward(request,response);
        }
        return new HashMap<String,Object>(){{
            put("msg","DML异常");
            put("success",false);
        }};
    }

    @ExceptionHandler(Exception.class)
    public Map<String,Object> exceptionAdvice(){
        return new HashMap<String,Object>(){{
            put("msg","其他异常");
            put("success",false);
        }};
    }
}
