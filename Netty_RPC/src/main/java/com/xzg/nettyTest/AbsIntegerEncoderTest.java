package com.xzg.nettyTest;

import org.junit.Assert;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class AbsIntegerEncoderTest {
	 @Test   //1
	    public void testEncoded() {
	        ByteBuf buf = Unpooled.buffer();  //2新建 ByteBuf 并写入负整数
	        for (int i = 1; i < 10; i++) {
	            buf.writeInt(i * -1);
	        }

	        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());  //3新建 EmbeddedChannel 并安装 AbsIntegerEncoder 来测试
	       
	        Assert.assertTrue(channel.writeOutbound(buf)); //4写 ByteBuf 并预测 readOutbound() 产生的数据	
	        Assert.assertTrue(channel.finish()); //5标记 channel 已经完成
	        
	        for (int i = 1; i < 10; i++) {
	            //Assert.assertEquals(i, channel.readOutbound());  //6读取产生到的消息，检查负值已经编码为绝对值
	        	 System.out.println(channel.readOutbound());
	        }
	        Assert.assertNull(channel.readOutbound());
	    }
}
