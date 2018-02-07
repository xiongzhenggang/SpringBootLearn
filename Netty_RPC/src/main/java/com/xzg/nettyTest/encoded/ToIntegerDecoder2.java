package com.xzg.nettyTest.encoded;


import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
/**ReplayingDecoder 是 byte-to-message 解码的一种特殊的抽象基类，
读取缓冲区的数据之前需要检查缓冲区是否有足够的字节，
使用ReplayingDecoder就无需自己检查；若ByteBuf中有足够的字节，则会正常读取；若没有足够的字节则会停止解码。*/
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {   //1实现继承自 ReplayingDecoder 用于将字节解码为消息

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        out.add(in.readInt());  //2从入站 ByteBuf 读取整型，并添加到解码消息的 List 中
    }
}