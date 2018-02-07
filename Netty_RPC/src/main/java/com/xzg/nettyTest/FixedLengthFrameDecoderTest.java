package com.xzg.nettyTest;

import org.junit.Assert;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
/**testFramesDecoded() 方法想测试一个 ByteBuf，这个ByteBuf 包含9个可读字节，
被解码成包含了3个可读字节的 ByteBuf。你可能注意到，它写入9字节到通道是通过调用 
writeInbound() 方法，之后再执行 finish() 来将 EmbeddedChannel 标记为已完成，
最后调用readInbound() 方法来获取 EmbeddedChannel 中的数据，直到没有可读字节。
testFramesDecoded2() 方法采取同样的方式，但有一个区别就是入站ByteBuf分两步写的，
当调用 writeInbound(input.readBytes(2)) 后返回 false 时，FixedLengthFrameDecoder 
值会产生输出，至少有3个字节是可读，testFramesDecoded2() 测试的工作相当于testFramesDecoded()。*/
public class FixedLengthFrameDecoderTest {

    @Test    //1测试增加 @Test 注解
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); //2新建 ByteBuf 并用字节填充它
        for (int i = 0; i < 12; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3)); //3新增 EmbeddedChannel 并添加 FixedLengthFrameDecoder 用于测试
        
        Assert.assertFalse(channel.writeInbound(input.readBytes(2))); //4写数据到 EmbeddedChannel
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());  //5标记 channel 已经完成
        //input read
        ByteBuf read = (ByteBuf) channel.readInbound();
        System.out.println(read.capacity());;
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }


    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }
}
