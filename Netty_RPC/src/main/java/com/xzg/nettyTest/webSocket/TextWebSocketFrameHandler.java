package com.xzg.nettyTest.webSocket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
/**上面显示了 TextWebSocketFrameHandler 仅作了几件事：
当WebSocket 与新客户端已成功握手完成，通过写入信息到 ChannelGroup 中的 Channel 来通知所有连接的客户端，然后添加新 
Channel 到 ChannelGroup如果接收到 TextWebSocketFrame，调用 retain() ，并将其写、刷新到 ChannelGroup，使所有连接的
WebSocket Channel 都能接收到它。和以前一样，retain() 是必需的，因为当 channelRead0（）返回时，TextWebSocketFrame的
引用计数将递减。由于所有操作都是异步的，writeAndFlush() 可能会在以后完成，我们不希望它访问无效的引用。由于 Netty 在其内部
处理了其余大部分功能，唯一剩下的需要我们去做的就是为每一个新创建的 Channel 初始化 ChannelPipeline 。要完成这个，我们需要一个ChannelInitializer*/
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> { 
	//1扩展 SimpleChannelInboundHandler 用于处理 TextWebSocketFrame 信息 
    private final ChannelGroup group;
    static Logger logger = Logger.getLogger(TextWebSocketFrameHandler.class);
    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
        PropertyConfigurator.configure("/home/xzg/java/workspace/netty_chat/src/log4j.properties");  
    }
    /**2.覆写userEventTriggered() 方法来处理自定义事件 */    
    	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
   //3如果接收的事件表明握手成功,就从 ChannelPipeline 中删除HttpRequestHandler ，因为接下来不会接受 HTTP 消息了
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
        	logger.info("如果是websocket则移除HttpRequestHandler的hander");
            ctx.pipeline().remove(HttpRequestHandler.class);    
            //4写一条消息给所有的已连接 WebSocket 客户端，通知它们建立了一个新的 Channel 连接
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            System.out.println(ctx.channel().isActive());
            //5添加新连接的 WebSocket Channel 到 ChannelGroup 中，这样它就能收到所有的信息
            group.add(ctx.channel());  
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    @Override
    //4.覆盖了 channelRead0() 事件处理方法。每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。其中如果你使用的是 Netty 5.x 版本时，需要把 channelRead0() 重命名为messageReceived()
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    	Channel incoming = ctx.channel();//获取
        for (Channel channel : group) {
            if (channel != incoming){//ws的链接
            	//这里转发到了所有的客户端
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
            }
        }
       // group.writeAndFlush(msg.retain()); //6.保留收到的消息，并通过 writeAndFlush() 传递给所有连接的客户端。
    }

    @Override
   // 覆盖了 handlerAdded() 事件处理方法。每当从服务端收到新的客户端连接时，客户端的 Channel 存入ChannelGroup列表中，并通知列表中的其他客户端 Channel
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  
        Channel incoming = ctx.channel();
        for (Channel channel : group) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
        }
        group.add(ctx.channel());
        System.out.println("Client:"+incoming.remoteAddress() +"加入");
    }

    @Override
    // 覆盖了 handlerRemoved() 事件处理方法。每当从服务端收到客户端断开时，客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  
        Channel incoming = ctx.channel();
        for (Channel channel : group) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        }
        System.out.println("Client:"+incoming.remoteAddress() +"离开");
        group.remove(ctx.channel());
    }

    @Override
    //覆盖了 channelInactive() 事件处理方法。服务端监听到客户端不活动
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"掉线");
    }
/**exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 
    错误或者处理器在处理事件时抛出的异常时。在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
    然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
