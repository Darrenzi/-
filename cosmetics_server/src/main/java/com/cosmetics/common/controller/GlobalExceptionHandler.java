package com.cosmetics.common.controller;

import com.cosmetics.common.bean.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 全局异常处理类
 */
@Slf4j
//@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理常数错误的异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<Object> ArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        return Response.fail("参数错误", e.getBindingResult().getAllErrors());
    }

    /**
     * 处理常数缺失的异常
     */
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    @ResponseBody
    public Response<Object> MissingServletRequestPartExceptionHandler(MissingServletRequestPartException e){
        return Response.fail("参数错误", e.getMessage());
    }

    /**
     * 处理请求方法错误
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Response<Object> HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
        return Response.fail("请求方法错误", e.getMessage());
    }

    /**
     * 处理所有异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<String> exceptionHandler(Exception e) {
        log.warn(e.getMessage());
        return Response.error("服务器出错或网络错误");
    }
}
