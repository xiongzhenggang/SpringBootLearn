package com.xzg.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String home() {
        System.out.println("index");
        return "/index";
    }
}
