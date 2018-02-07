package com.xzg.javaSimpleRpc;
/**先定义一个接口，这个接口就是客户端向服务端发起调用所使用的接口*/
public interface EchoService {
	String echo(String request);
	String ech(String request);
}
