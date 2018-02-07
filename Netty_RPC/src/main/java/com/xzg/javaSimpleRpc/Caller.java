package com.xzg.javaSimpleRpc;

import java.lang.reflect.Proxy;
/**把socket通信都隐藏起来，让客户端只知道调用echo接口*/
public class Caller {
	public static void main(String args[]) {
		//动态代理执行
        EchoService echo = (EchoService)Proxy.newProxyInstance(EchoService.class.getClassLoader(),
                new Class<?>[]{EchoService.class}, new DynamicProxyHandler());
        for (int i = 0; i < 3; i++) {
            System.out.println(echo.echo(String.valueOf(i)));
        }
       // echo.ech("dd");
    }
}
