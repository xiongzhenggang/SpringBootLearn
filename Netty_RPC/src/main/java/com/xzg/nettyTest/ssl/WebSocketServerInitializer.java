package com.xzg.nettyTest.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
/**类处理协议升级握手以及三个“控制”帧 Close,
Ping 和 Pong。Text 和 Binary 数据帧将被传递到下一个处理程序(由你实现)进行处理。*/
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),  //1添加 HttpObjectAggregator 用于提供在握手时聚合 HttpRequest
//添加 WebSocketServerProtocolHandler 用于处理色好给你寄握手如果请求是发送到"/websocket." 端点，当升级完成后，它将会处理Ping, Pong 和 Close 帧
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),  //3TextFrameHandler 将会处理 TextWebSocketFrames
                new BinaryFrameHandler(),  //4BinaryFrameHandler 将会处理 BinaryWebSocketFrames
                new ContinuationFrameHandler());  //5ContinuationFrameHandler 将会处理ContinuationWebSocketFrames
    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // Handle text frame
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
            // Handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            // Handle continuation frame
        }
    }
}