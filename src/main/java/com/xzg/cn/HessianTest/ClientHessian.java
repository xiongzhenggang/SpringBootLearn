package com.xzg.cn.HessianTest;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xzg.cn.hessianService.HelloService;

import java.net.MalformedURLException;
//测试不适用spring的Hessian客户端使用
public class ClientHessian {
    public static void main(String[] args) {
                String url = "http://localhost:8080/HelloWorldService";

                try {
                    HessianProxyFactory  factory = new HessianProxyFactory();
                    factory.setOverloadEnabled(true);
                    HelloService service = (HelloService) (factory).create(HelloService.class, url);
                        System.out.println(service.getSpitter());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
           }
}
