package com.xzg.nettyRpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xzg
 *	加入到pipleline中的编码器
 */
@SuppressWarnings("rawtypes")
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
    	//Object in,参数表示要编码的信息。
        if (genericClass.isInstance(in)) {
        	//如需要替换其它序列化框架，只需修改SerializationUtil即可
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}