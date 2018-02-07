package com.xzg.nettyRpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xzg.nettyRpc.protocol.RpcDecoder;
import com.xzg.nettyRpc.protocol.RpcEncoder;
import com.xzg.nettyRpc.protocol.RpcRequest;
import com.xzg.nettyRpc.protocol.RpcResponse;
import com.xzg.nettyRpc.registry.ServiceRegistry;

/**
 * @author xzg
 *	rpc服务端，
 *实现ApplicationContextAware的bean，会在Bean初始化的时候自动调用setApplicationContext，
 *然后就可以获取spring的上下文，通过spring的上下文就可以获取到spring的一些信息，还可以获取其他的bean
 *实现InitializingBean，在bean初始化之前会调用afterPropertiesSet()
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private String serverAddress;
    private ServiceRegistry serviceRegistry;
    private Map<String, Object> handlerMap = new HashMap<String, Object>(); // 存放接口名与服务对象之间的映射关系
    private static ThreadPoolExecutor threadPoolExecutor;
    /**
     * @param serverAddress
     * 如果不使用zookepeeper发现地址，就自己定义
     */
    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * @param serverAddress
     * @param serviceRegistry
     * 使用zookpeeper的管理功能
     */
    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    /** 
     * rpcServer初始化的时候调用，目的是将所有添加了注解RpcService的注解类找到
     * 获取其接口名称，作为key，添加该注解的类作为value封装到handlemap中。
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    	// 获取所有带有 RpcService 注解的 Spring Bean
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    /**
     * 在bean初始化之前会调用afterPropertiesSet()
     * 通过初始化时，启动netty—rpcserver。并且在通道中添加相应的处理器。
     * 其中RpcHandler处理器的作用为
     */
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//启动服务端
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                        	.addLast(new  LengthFieldBasedFrameDecoder(65536,0,4,0,0))// 处理tcp粘包问题
                            .addLast(new RpcDecoder(RpcRequest.class)) // 服务端，将 RPC 请求进行解码（为了处理请求）
                            .addLast(new RpcEncoder(RpcResponse.class)) // 服务端，将 RPC 响应进行编码（为了返回响应）
                            .addLast(new RpcHandler(handlerMap)); // 做后处理 RPC 请求
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            LOGGER.debug("server started on port {}", port);
            if (serviceRegistry != null) {
                serviceRegistry.register(serverAddress); // 注册服务地址
            }

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void submit(Runnable task){
        if(threadPoolExecutor == null){
            synchronized (RpcServer.class) {
                if(threadPoolExecutor == null){
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
}
}