package com.dljd.crm.aop;

import com.dljd.crm.exception.CRUDException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

@Aspect
@Component
public class MyAop {

    //所有的查询方法
    @Pointcut("execution(* com.dljd.crm.services.*.get*(..))")
    public void getPointcut(){}

    //所有的增删改方法
    @Pointcut("(execution(* com.dljd.crm.services.*.add*(..)))||(execution(* com.dljd.crm.services.*.delete*(..)))||(execution(* com.dljd.crm.services.*.update*(..)))")
    public void CUDPointcut(){}

    //拦截器方法
    @Pointcut("execution(* com.dljd.crm.controller.interceptor.LoginInterceptor.preHandle(..))")
    public void interceptorPointcut(){}

    //所有的getSome方法
    @Pointcut("execution(* com.dljd.crm.services.*.getSome*(..))")
    public void getSomePointcut(){}

    /**
     * 判断DQL语句的执行结果是否合法，若不合法，抛出自定义CRUDException，由异常处理器接收并进行处理
     * @param result 查询结果
     */
    @AfterReturning(value = "getPointcut()",returning = "result")
    public void getThrow(Object result){
        if(result==null){
            throw new CRUDException("查询失败！！！");
        }
    }

    /**
     * 判断DML语句执行结果是否合法，若不合法，抛出自定义异常CRUDException，事务管理器根据异常类型进行回滚操作，异常处理器接收异常并进行后续处理
     * @param result DML语句执行后影响的记录行数
     */
    @AfterReturning(value = "CUDPointcut()",returning = "result")
    public void CUDThrow(Object result){
        System.out.println((int)result);
        if ((int) result < 1){
            throw new CRUDException("操作失败！！！");
        }
    }

    /**
     * 该切面方法的切入点是拦截器，需要在springMVC配置文件中声明AspectJ注解驱动才可切入
     * @param jp 目标方法的信息
     * @param result 目标方法的返回值
     */
    @AfterReturning(value = "interceptorPointcut()",returning = "result")
    public void interceptor(JoinPoint jp,Object result){
        HttpServletRequest request = (HttpServletRequest) jp.getArgs()[0];
        String requestURI = request.getRequestURI();
        System.out.println("LoginInterceptor拦截器："+requestURI+"---->"+result);
    }

    @Before("getSomePointcut()")
    public void getSome(JoinPoint jp){

    }
}
