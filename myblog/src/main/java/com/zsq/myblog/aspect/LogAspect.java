package com.zsq.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志处理类
 */

@Aspect     //开启aop
@Component  //注册为组件
public class LogAspect {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //设置切点,位置在web包下的所有内容
    @Pointcut("execution(* com.zsq.myblog.web.*.*(..))")
    public void log(){}

    /*********************************************************
     *
     * 通过aop来实现日志记录请求url，访问者的ip，调用方法，参数，返回内容等
     *
     * 在获取浏览器请求信息的时候我们需要使用HttpServletRequest，但是不能
     * 直接使用来完成，需要按下列方法实现
     *
     * JoinPoint对象是封装了吗aop面向切面的信息，使用他可以获取这些信息
     *
     *********************************************************/

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        //用ServletRequestAttributes获取并记录接收到的request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取request
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //joinPoint.getSignature()获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        //获取传入的参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        LOGGER.info("Result : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        LOGGER.info("-----------doAfter------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        LOGGER.info("Result: {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] aegs;

        public RequestLog(String url, String ip, String classMethod, Object[] aegs) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.aegs = aegs;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", aegs=" + Arrays.toString(aegs) +
                    '}';
        }
    }

}
