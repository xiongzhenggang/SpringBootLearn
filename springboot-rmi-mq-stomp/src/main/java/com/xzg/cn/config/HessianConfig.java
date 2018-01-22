package com.xzg.cn.config;

import com.xzg.cn.hessianService.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianConfig {
    //不要让spring security拦截
    @Bean(name = "/HelloWorldService")
    //HessianServiceExporter是一个spring mvc的控制器，他接受Hessian请求，并将这些请求转换成
    //对到处pojo的方法调用。HessianServiceExporter会把helloService bean到处为Heassian服务
    public HessianServiceExporter hessianHelloServiceExporter(HelloService helloService){
        HessianServiceExporter export = new HessianServiceExporter();
        export.setService(helloService);
        export.setServiceInterface(HelloService.class);
        return export;
    }
}
