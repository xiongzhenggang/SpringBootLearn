/**
 * 
 */
package com.xzg.nettyTest.encoded;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author xzg
 *
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> { //1

@Override
public void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out)
    throws Exception {
out.add(String.valueOf(msg)); //2通过 String.valueOf() 转换 Integer 消息字符串
}
}