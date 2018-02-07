package com.xzg.nettyTest;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {  //1 MessageToMessageEncoder 用于编码消息到另外一种格式
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) { //2
            int value = Math.abs(in.readInt());//3读取下一个输入 ByteBuf 产出的 int 值，并计算绝对值
            out.add(value);  //4写 int 到编码的消息 List 
        }
    }
}