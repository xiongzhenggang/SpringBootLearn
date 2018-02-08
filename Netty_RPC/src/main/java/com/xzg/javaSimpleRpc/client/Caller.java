package com.xzg.javaSimpleRpc.client;

import java.lang.reflect.Proxy;
/**把socket通信都隐藏起来，让客户端只知道调用echo接口*/
public class Caller {
	public static void main(String args[]) {
		//jdk的动态代理是基于接口的，所以这里的接口与服务端相同
        EchoService echo = (EchoService)Proxy.newProxyInstance(
        		//使用该接口的类加载器，为DynamicProxyHandler
        		EchoService.class.getClassLoader(),
                new Class<?>[]{EchoService.class},
              //DynamicProxyHandler为获取服务端返回的对象包装成代理
                new DynamicProxyHandler());
        //客户端使用
        for (int i = 0; i < 3; i++) {
            System.out.println(echo.echo(String.valueOf(i)));
        }
    }
}
