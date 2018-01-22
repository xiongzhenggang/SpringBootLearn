package com.xzg.cn.config;

import com.xzg.cn.hessianService.HelloService;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RmiConfig {
    //RmiServiceExporter 将HelloService发布为RMI服务
    public RmiServiceExporter rmiServiceExporter(HelloService helloService){
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(helloService);
        rmiServiceExporter.setServiceName("HelloService");
        rmiServiceExporter.setServiceInterface(HelloService.class);
        return rmiServiceExporter;
    }
}
