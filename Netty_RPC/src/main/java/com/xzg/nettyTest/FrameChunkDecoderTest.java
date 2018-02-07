package com.xzg.nettyTest;

import org.junit.Assert;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;

public class FrameChunkDecoderTest {

    @Test    //1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();  //2新建 ByteBuf 写入 9 个字节
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
       // System.out.println(input.getByte(0)==buf.getByte(0));
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));  //3新建 EmbeddedChannel 并安装一个 FixedLengthFrameDecoder 用于测试
        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));  //4写入 2 个字节并预测生产的新帧(消息)
        try {
            channel.writeInbound(input.readBytes(4)); //5写一帧大于帧的最大容量 (3) 并检查一个 TooLongFrameException 异常
            Assert.fail();  //6如果异常没有被捕获，测试将失败。注意如果类实现 exceptionCaught() 并且处理了异常 exception，那么这里就不会捕捉异常
        } catch (TooLongFrameException e) {
            // expected
        	e.printStackTrace();
        }
        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));  //7写剩余的 2 个字节预测一个帧
        Assert.assertTrue(channel.finish());  //8标记 channel 完成

        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(2), read); //9读到的产生的消息并且验证值。注意 assertEquals(Object,Object)测试使用 equals() 是否相当，不是对象的引用是否相当
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();

        buf.release();
    }
}