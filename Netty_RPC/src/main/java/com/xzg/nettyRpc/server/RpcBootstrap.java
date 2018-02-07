package com.xzg.nettyRpc.server;


import org.springframework.context.support.ClassPathXmlApplicationContext;


public class RpcBootstrap {

	 @SuppressWarnings("resource")
	public static void main(String[] args) {
		//首先加载启动服务端。监听客户端的到来
	        new ClassPathXmlApplicationContext("server-spring.xml");
}
}
