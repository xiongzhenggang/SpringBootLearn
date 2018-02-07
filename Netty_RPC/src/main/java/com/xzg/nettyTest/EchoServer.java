/**
 * 
 */
package com.xzg.nettyTest;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hasee
 * @TIME 2017年3月27日
 * 注意类的隐藏和实例创建
 */
public class EchoServer {
	private final int port;

    public EchoServer(int port) {
        this.port = port;
    }
        public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                    " <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);        //1设置端口值（抛出一个 NumberFormatException 如果该端口参数的格式不正确）
        new EchoServer(port).start();                //2.呼叫服务器的 start() 方法
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(); //3创建并分配一个 NioEventLoopGroup 实例来处理事件的处理，如接受新的连接和读/写数据。
        try {
            ServerBootstrap b = new ServerBootstrap();//创建 ServerBootstrap 实例来引导服务器并随后绑定
            b.group(group)                                //4.创建 ServerBootstrap
             .channel(NioServerSocketChannel.class)        //5指定使用 NIO 的传输 Channel
             .localAddress(new InetSocketAddress(port))    //6设置 socket 地址使用所选的端口
             .childHandler(new ChannelInitializer<SocketChannel>() { //7.添加 EchoServerHandler 到 Channel 的 ChannelPipeline
            	 /*在这里我们使用一个特殊的类，ChannelInitializer 。当一个新的连接被接受，一个新的子 Channel 将被创建，
            	 ChannelInitializer 会添加我们EchoServerHandler
            	 的实例到 Channel 的 ChannelPipeline。正如我们如前所述，如果有入站信息，这个处理器将被通知。*/
                 @Override
                 public void initChannel(SocketChannel ch) 
                     throws Exception {
                     ch.pipeline().addLast(
                             new EchoServerHandler());//EchoServerHandler 实例给每一个新的 Channel 初始化
                 }
             });

            ChannelFuture f = b.bind().sync();            //8绑定的服务器;sync 等待服务器关闭，调用 sync() 的原因是当前线程阻塞
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();            //9关闭 channel 和 块，直到它被关闭
        } finally {
            group.shutdownGracefully().sync();            //10关机的 EventLoopGroup，释放所有资源。
        }
    }
}