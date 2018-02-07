package com.xzg.nettyTest.bytebuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class WriteHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx;        //1存储 ChannelHandlerContext 的引用供以后使用
    }

    public void send(String msg) {
        ctx.writeAndFlush(msg);  //2使用之前存储的 ChannelHandlerContext 来发送消息
    }
}

