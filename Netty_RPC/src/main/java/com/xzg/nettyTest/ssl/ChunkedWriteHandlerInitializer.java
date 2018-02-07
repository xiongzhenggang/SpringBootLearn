package com.xzg.nettyTest.ssl;

import java.io.File;
import java.io.FileInputStream;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;
import javax.net.ssl.SSLContext;

public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    private final File file;
    private final SSLContext sslCtx;

    public ChunkedWriteHandlerInitializer(File file, SSLContext sslCtx) {
        this.file = file;
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SslHandler(sslCtx.createSSLEngine())); //1添加 SslHandler 到 ChannelPipeline.
        pipeline.addLast(new ChunkedWriteHandler());//2添加 ChunkedWriteHandler 用来处理作为 ChunkedInput 传进的数据
        pipeline.addLast(new WriteStreamHandler());//3当连接建立时，WriteStreamHandler 开始写文件的内容
    }

    /**当连接建立时，channelActive() 触发使用 ChunkedInput 
    来写文件的内容 (插图显示了 FileInputStream;也可以使用任何 InputStream )*/
    public final class WriteStreamHandler extends ChannelInboundHandlerAdapter {  //4

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
