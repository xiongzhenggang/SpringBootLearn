package hello;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.xzg.cn"})
//这个注解扫描@WebFilter, @WebListener and @WebServlet，所在的包
@ServletComponentScan(basePackages = "com.xzg.cn.filter")
//@ServletComponentScan
public class SampleController {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "index";
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}