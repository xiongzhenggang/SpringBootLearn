package com.xzg.nettyTest.bytebuf;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**因为 ChannelHandler 可以属于多个 ChannelPipeline ,它可以绑定多个 ChannelHandlerContext 
实例。然而,ChannelHandler 用于这种用法必须添加@Sharable 注解。否则,试图将它添加到多个 
ChannelPipeline 将引发一个异常。此外,它必须既是线程安全的又能安全地使用多个同时的通道(比如,连接)。*/
@Sharable            //1添加 @Sharable 注解
public class SharableHandler extends ChannelInboundHandlerAdapter {
/*	使用@Sharable的话，要确定 ChannelHandler 是线程安全的。*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channel read message " + msg);
        ctx.fireChannelRead(msg);  //2日志方法调用， 并专递到下一个 ChannelHandler
    }
}