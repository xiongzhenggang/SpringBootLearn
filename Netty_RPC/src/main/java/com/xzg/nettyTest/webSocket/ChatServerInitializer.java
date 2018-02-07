/**
 * 
 */
package com.xzg.nettyTest.webSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xzg
 * initChannel() 方法用于设置所有新注册的 Channel 的ChannelPipeline,安装所有需要的 ChannelHandler。
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {   //1.扩展 ChannelInitializer
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }
  /**2添加 ChannelHandler　到 ChannelPipeline
   * WebSocketServerProtocolHandler 处理所有规定的 WebSocket 帧类型和升级握手本身。
   * 如果握手成功所需的 ChannelHandler 被添加到管道，而那些不再需要的则被去除。
   * 这代表了 ChannelPipeline 刚刚经过 ChatServerInitializer 初始化。
   * */   
    @Override
    protected void initChannel(Channel ch) throws Exception {  
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}