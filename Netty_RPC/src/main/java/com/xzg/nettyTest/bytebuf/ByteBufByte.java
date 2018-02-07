package com.xzg.nettyTest.bytebuf;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;

public class ByteBufByte {
	
	public static void main(String[] args){
		//ByteBuffer buffer = ByteBuffer.wrap("aa".getBytes());//将字符序列包装到缓冲区中。
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1创建一个 ByteBuf 保存特定字节串。
		//若需要操作某段数据，使用 slice(int, int
		ByteBuf sliced = buf.slice(0, 14);          //2创建从索引 0 开始，并在 14 结束的 ByteBuf 的新 slice。
		System.out.println(sliced.toString(utf8));  //3打印 Netty in Action
		buf.setByte(0, (byte) 'J');                 //4更新索引 0 的字节。
		System.out.println(buf.getByte(0) == sliced.getByte(0));;//断言成功，因为数据是共享的，并以一个地方所做的修改将在其他地方可见
	///////////////////////////////////////////////////////
		Charset utf801 = Charset.forName("UTF-8");
		ByteBuf buf01 = Unpooled.copiedBuffer("Netty in Action rocks!", utf801);     //1

		ByteBuf copy = buf01.copy(0, 14);  //2创建从索引0开始和 14 结束 的 ByteBuf 的段的拷贝。
		System.out.println(copy.toString(utf801));      //3

		buf01.setByte(0, (byte) 'A');       //4
		System.out.println(buf01.getByte(0) == copy.getByte(0));//.断言成功，因为数据不是共享的，并以一个地方所做的修改将不影响其他
		System.out.println(buf.writerIndex()+":"+buf.readerIndex());
		copy.writeBytes(buf, 22);
		System.out.println(buf01.getByte(0) +":"+ copy.getByte(0));
	//////////////////////////////////////////////////////////////////
		Charset utf802= Charset.forName("UTF-8");
		ByteBuf buf02 = Unpooled.copiedBuffer("Netty in Action rocks!", utf802);    //1
		System.out.println((char)buf02.getByte(0));     //2打印的第一个字符，N
		int readerIndex = buf02.readerIndex();     //3存储当前 readerIndex 和 writerIndex
		int writerIndex = buf02.writerIndex();
		buf02.setByte(0, (byte)'B');      //44.更新索引 0 的字符B
		System.out.println((char)buf02.getByte(0));    //55.打印出的第一个字符，现在B
		assert readerIndex == buf02.readerIndex();  //66.这些断言成功，因为这些操作永远不会改变索引
		assert writerIndex ==  buf02.writerIndex();
		nettywrite();
	}
	static void nettywrite(){
		System.out.println("========================");
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);    //1
		System.out.println((char)buf.readByte());                    //2

		int readerIndex = buf.readerIndex();                        //3存储当前的 readerIndex
		int writerIndex = buf.writerIndex();                        //4
		System.out.println(readerIndex+":"+writerIndex);
		buf.writeByte((byte)'?');                            //5
		assert readerIndex == buf.readerIndex();
		assert writerIndex != buf.writerIndex();
	}

/** 一个 ChannelPipeline 是用来保存关联到一个 Channel 的ChannelHandler
    可以修改 ChannelPipeline 通过动态添加和删除 ChannelHandler
    ChannelPipeline 有着丰富的API调用动作来回应入站和出站事件。
    每一个创建新 Channel ,分配一个新的 ChannelPipeline。这个关联是 永久性的;Channel
   既不能附上另一个 ChannelPipeline 也不能分离 当前这个。这是一个 Netty 的固定方面的组件生命周期,开发人员无需特别处理。
    */

	static void nettyChannelPiple(){
		ChannelPipeline pipeline = null; // get reference to pipeline;
		DiscardHandler firstHandler = new DiscardHandler(); //1创建一个 FirstHandler 实例
		pipeline.addLast("handler1", firstHandler); //2添加该实例作为 "handler1" 到 ChannelPipeline 
		pipeline.addFirst("handler2", new DiscardHandler());//3添加 SecondHandler 实例作为 "handler2" 到 ChannelPipeline 的第一个槽，这意味着它将替换之前已经存在的 "handler1"
		pipeline.addLast("handler3", new DiscardHandler()); //4
		pipeline.remove("handler3"); //5添加 ThirdHandler 实例作为"handler3" 到 ChannelPipeline 的最后一个槽
		pipeline.remove(firstHandler); //6 
		pipeline.replace("handler2", "handler4", new DiscardHandler()); //6
	}
	
	/**接口 ChannelHandlerContext 代表 ChannelHandler 和ChannelPipeline 之间的关联,
	并在 ChannelHandler 添加到 ChannelPipeline 时创建一个实例。ChannelHandlerContext
	的主要功能是管理通过同一个 ChannelPipeline 关联的 ChannelHandler 之间的交互。*/

}
