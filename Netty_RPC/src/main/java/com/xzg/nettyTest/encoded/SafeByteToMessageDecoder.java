package com.xzg.nettyTest.encoded;


import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class SafeByteToMessageDecoder extends ByteToMessageDecoder {  //1
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if (readable > MAX_FRAME_SIZE) { //2检测缓冲区数据是否大于 MAX_FRAME_SIZE 
 //忽略所有可读的字节，并抛出 TooLongFrameException 来通知 ChannelPipeline 中的 ChannelHandler 这个帧数据超长
            in.skipBytes(readable);  
            throw new TooLongFrameException("Frame too big!");
        }
        // do something
    }
}