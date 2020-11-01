package com.zsq.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类：用于处理页面请求异常
 * @ControllerAdvice--->全局异常处理;全局数据绑定;全局数据预处理
 *
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //该注解是实现异常处理的
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {

        LOGGER.error("Request URL : {},Exception : {}",request.getRequestURI(),e);//获取出现异常的URL地址和异常信息(控制台日志)

        //处理自定义异常状态，交给spring处理，但存在该状态时，抛出异常
        if (AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        //添加信息到视图模型中
        mv.addObject("url",request.getRequestURI());
        mv.addObject("exception",e);
        //设置返回的页面
        mv.setViewName("error/error");

        return mv;
    }
}
