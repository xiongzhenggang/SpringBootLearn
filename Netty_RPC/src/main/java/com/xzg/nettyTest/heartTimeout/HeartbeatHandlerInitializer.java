package com.xzg.nettyTest.heartTimeout;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author xzg
 *特殊的类，ChannelInitializer 。当一个新的连接被接受，一个新的子 Channel 将被创建，
            	 ChannelInitializer 会添加到server类
 */
public class HeartbeatHandlerInitializer extends ChannelInitializer<Channel> {
	 
    private static final int READ_IDEL_TIME_OUT = 4; // 读超时
    private static final int WRITE_IDEL_TIME_OUT = 5;// 写超时
    private static final int ALL_IDEL_TIME_OUT = 7; // 所有超时
 
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS)); // 1
        pipeline.addLast(new HeartbeatServerHandler()); // 2 定义了一个 HeartbeatServerHandler 处理器，用来处理超时时，发送心跳
    }
}
