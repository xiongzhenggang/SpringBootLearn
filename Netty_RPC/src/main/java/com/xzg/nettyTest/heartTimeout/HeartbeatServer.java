package com.xzg.nettyTest.heartTimeout;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public final class HeartbeatServer {
	 
    static final int PORT = 8082;
 
    public static void main(String[] args) throws Exception {
  
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
            //handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行，这是两者的区别。
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new HeartbeatHandlerInitializer());
 
            // Start the server.绑定的服务器;sync 等待服务器关闭，调用 sync() 的原因是当前线程阻塞
            ChannelFuture f = b.bind(PORT).sync();
 
            // Wait until the server socket is closed. 关闭 channel 和 块，直到它被关闭
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
