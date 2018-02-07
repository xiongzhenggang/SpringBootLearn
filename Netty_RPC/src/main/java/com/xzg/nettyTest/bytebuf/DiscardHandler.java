package com.xzg.nettyTest.bytebuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class DiscardHandler extends ChannelInboundHandlerAdapter { //11.扩展 ChannelInboundHandlerAdapter
	

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                                     Object msg) {
    	
    	ChannelPipeline pipeline = ctx.pipeline(); //1
    	pipeline.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));  //2通过 ChannelPipeline 写缓冲区
        ReferenceCountUtil.release(msg); //22.ReferenceCountUtil.release() 来丢弃收到的信息
    }

}
