package com.xzg.nettyTest;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder { //1继承 ByteToMessageDecoder 用来处理入站的字节并将他们解码为消息

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) { //2指定产出的帧的长度
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= frameLength) { //3检查是否有足够的字节用于读到下个帧
            ByteBuf buf = in.readBytes(frameLength);//4从 ByteBuf 读取新帧
            out.add(buf); //5添加帧到解码好的消息 List 
        }
    }
}
