package com.xzg.nettyRpc.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@RpcService(HelloService.class)// 指定远程接口
public class HelloServiceImpl implements HelloService {
	
	public String hello(String name) {
		// TODO Auto-generated method stub
		return "hello:"+name;
	}

	public void serverInfo() {
		// TODO Auto-generated method stub
		 InetAddress s = null;
		try {
			s = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(null != s.getHostName())
			LOGGER.debug("本机的hostname==>{}", s.getHostName());
		//本机hostname
		//本机的信息
		  Properties props = System.getProperties(); 
		  LOGGER.debug("Java的虚拟机实现版本：{}", props.getProperty("java.vm.version"));
		  LOGGER.debug("操作系统的名称：：{}", props.getProperty("os.name"));
		  LOGGER.debug("用户的当前工作目录：{}", props.getProperty("user.dir"));
	}

}
