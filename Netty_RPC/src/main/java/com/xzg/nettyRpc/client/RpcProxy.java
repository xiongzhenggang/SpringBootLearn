package com.xzg.nettyRpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.xzg.nettyRpc.protocol.RpcRequest;
import com.xzg.nettyRpc.protocol.RpcResponse;
import com.xzg.nettyRpc.registry.ServiceDiscovery;

/**
 * @author xzg
 *代理模式调用服务
 */
public class RpcProxy {

    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    /**
     * @param interfaceClass
     * @return
     * 动态代理获取代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            /**
             * @author xzg
             *	newProxyInstance第三个参数，重写invoke方法
             */
            new InvocationHandler() {
            	//invoke方法主要是和服务端通信获取服务端发送过来的代理对象
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                	//将客户端发送的请求封装对象，序列化传输
                    RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);
                    //zookeeper中的发现服务目的是通过zookeeper作为服务端的管理者，当多个服务做负载均衡可以不用人工去干预
                    if (serviceDiscovery != null) {
                        serverAddress = serviceDiscovery.discover(); // 发现服务
                    }

                    String[] array = serverAddress.split(":");
                    String host = array[0];
                    int port = Integer.parseInt(array[1]);

                    RpcClient client = new RpcClient(host, port); // 初始化 RPC 客户端
                    RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应

                    if (response.isError()) {
                        throw response.getError();
                    } else {
                        return response.getResult();
                    }
                }
            }
        );
    }
}