package com.xzg.cn.controller;

import com.xzg.cn.entity.Spitter;
import com.xzg.cn.webstomp.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String home() {
        return "/index";
    }

}
