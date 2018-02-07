package com.xzg.nettyTest;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class FrameChunkDecoder extends ByteToMessageDecoder {  //1继承 ByteToMessageDecoder 用于解码入站字节到消息

    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();  //2指定最大需要的帧产生的体积
        if (readableBytes > maxFrameSize)  {
            // discard the bytes   //3如果帧太大就丢弃并抛出一个 TooLongFrameException 异常
            in.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(readableBytes); //4同时从 ByteBuf 读到新帧
        out.add(buf);  //5添加帧到解码消息 List
    }
}