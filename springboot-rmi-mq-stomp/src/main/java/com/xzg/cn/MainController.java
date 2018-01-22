package com.xzg.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@EnableCaching
@ComponentScan(basePackages={"com.xzg.cn"})
//这个注解扫描@WebFilter, @WebListener and @WebServlet，所在的包
//@ServletComponentScan
public class MainController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "index";
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainController.class, args);
    }
}