package com.xzg.javaSimpleRpc.server;

import com.xzg.javaSimpleRpc.client.EchoService;

/**
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * @author xzg
 * 2018年2月8日
 * @ClassName: EchoServiceImple.java
 * @Description: 服务端具有相同的接口以及实现类（因为是本地这里服务端省略EchoService接口，直接使用客户端的接口）
 * @version: v1.0.0
 */
public class EchoServiceImple implements EchoService{

	public String echo(String request) {
		// TODO Auto-generated method stub
		return "echo:"+request;
	}

	public String read(String request) {
		// TODO Auto-generated method stub
		return "服务端发送添加 "+request;
	}
}
