package com.xzg.nettyRpc.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.xzg.nettyRpc.client.RpcProxy;
import com.xzg.nettyRpc.server.HelloService;

@RunWith(value = SpringJUnit4ClassRunner.class)
//xml地址需要在该包下
@ContextConfiguration(locations = "client-spring.xml")
//@ContextConfiguration(locations = "spring.xml")
public class HelloServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceTest.class);
    @Resource
    private RpcProxy rpcProxy;
    //测试启动客户端
    @Test
    public void helloTest() {
    	//启动rpc服务端
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("World");
        LOGGER.debug("取得结果=====:{}", result);
        helloService.serverInfo();
    }
}