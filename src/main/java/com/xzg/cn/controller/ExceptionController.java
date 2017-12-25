package com.xzg.cn.controller;

import com.xzg.cn.expxetion.MyClassNotFind;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExceptionController {

    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        if(true){
            throw  new MyClassNotFind();
        }
        return "uploadimg";
    }
}
