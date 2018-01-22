//package com.xzg.cn.config;
//
//import com.xzg.cn.hessianService.HelloService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
//
//@Configuration
//public class HttpInvokerClientConfig {
//    @Bean
//    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(){
//        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
//        proxy.setServiceUrl("http://localhost:8080/Hello/hello.service/");
//        proxy.setServiceInterface(HelloService.class);
//        return proxy;
//    }
//    //然后就可以直接使用@Autowired注入HelloService使用
//}
