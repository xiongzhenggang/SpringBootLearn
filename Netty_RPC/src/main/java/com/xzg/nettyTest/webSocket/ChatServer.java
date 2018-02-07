package com.xzg.nettyTest.webSocket;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
/**
 * @author xzg
 */
public class ChatServer {
	//1创建 DefaultChannelGroup 用来保存所有连接的的 WebSocket channel(创建一个用于管理channel的组，默认是使用零拷贝的）
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    //管理event事件
    private final EventLoopGroup eventLoopGroupgroup = new NioEventLoopGroup();
    private  static Logger logger = Logger.getLogger(ChatServer.class);
    private Channel channel;
    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap  = new ServerBootstrap(); //2引导 服务器
        bootstrap.group(eventLoopGroupgroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup))
                .option(ChannelOption.SO_BACKLOG, 128)//设置这里指定的 Channel 实现的配置参数。我们正在写一个TCP/IP 的服务端，因此我们被允许设置 socket 的参数选项比如tcpNoDelay 和 keepAlive
                .childOption(ChannelOption.SO_KEEPALIVE, true);//.option() 是提供给NioServerSocketChannel用来接收进来的连接。childOption() 是提供给由父管道ServerChannel接收到的连接
        ChannelFuture future = bootstrap.bind(address);
        
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }
  //3创建 ChannelInitializer
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {   
       return new ChatServerInitializer(group);
    }

    public void destroy() {   //4处理服务器关闭，包括释放所有资源
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        eventLoopGroupgroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception{
    	logger.info("初始化log。。。。");
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        PropertyConfigurator.configure( "/home/xzg/java/workspace/netty_chat/src/log4j.properties" );
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}