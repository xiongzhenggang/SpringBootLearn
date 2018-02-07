package com.xzg.nettyRpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface HelloService {
	
 static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

	String hello(String name);
	
	void serverInfo();
}
