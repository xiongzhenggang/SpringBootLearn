package com.xzg.cn.config;

import com.xzg.cn.hessianService.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

//配置httpInvoker
@Configuration
public class HttpInvokerConfig {
    //HttpInvokerServiceExporter 是一个sprinvmvc的控制器，需要url映射
    @Bean
    public HttpInvokerServiceExporter httpExporterHelloService(HelloService helloService){
        HttpInvokerServiceExporter exporter =
                new HttpInvokerServiceExporter();
        exporter.setService(helloService);
        exporter.setServiceInterface(HelloService.class);
        return exporter;
    }
    //需要url映射
    @Bean
    public HandlerMapping handlerMapping(){
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        //httpExporterHelloService 注入上面的bean
        mappings.setProperty("/hello.service","httpExporterHelloService");
        mapping.setMappings(mappings);
        return mapping;
    }
}
