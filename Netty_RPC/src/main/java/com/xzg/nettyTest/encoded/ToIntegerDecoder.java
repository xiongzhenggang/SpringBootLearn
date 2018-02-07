package com.xzg.nettyTest.encoded;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**每次从入站的 ByteBuf 读取四个字节，解码成整形，并添加到一个 List （本例是指 Integer）,
当不能再添加数据到 lsit 时，它所包含的内容就会被发送到下个 ChannelInboundHandler*/
public class ToIntegerDecoder extends ByteToMessageDecoder {  //1实现继承了 ByteToMessageDecode 用于将字节解码为消息

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        if (in.readableBytes() >= 4) {  //2检查可读的字节是否至少有4个 ( int 是4个字节长度)
            out.add(in.readInt());  //3从入站 ByteBuf 读取 int ， 添加到解码消息的 List 中
        }
    }
}