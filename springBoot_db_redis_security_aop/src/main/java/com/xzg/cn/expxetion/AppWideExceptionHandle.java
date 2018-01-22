package com.xzg.cn.expxetion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandle {
    @ExceptionHandler(MyClassNotFind.class)
    public String myExceptionHandle(){
        System.out.println("返回错误页面");
        return "error";
    }
}
