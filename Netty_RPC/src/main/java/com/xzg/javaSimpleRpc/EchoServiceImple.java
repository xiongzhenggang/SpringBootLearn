package com.xzg.javaSimpleRpc;
//接口实现类
public class EchoServiceImple implements EchoService{

	public String echo(String request) {
		// TODO Auto-generated method stub
		return "echo:"+request;
	}

	public String ech(String request) {
		// TODO Auto-generated method stub
		return null;
	}
}
