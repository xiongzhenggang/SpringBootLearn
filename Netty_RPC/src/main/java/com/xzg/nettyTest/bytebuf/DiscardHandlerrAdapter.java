package com.xzg.nettyTest.bytebuf;

import io.netty.channel.ChannelOutboundHandlerAdapter;
/**ChannelOutboundHandler 提供了出站操作时调用的方法。
这些方法会被 Channel, ChannelPipeline, 和 ChannelHandlerContext 调用。
ChannelOutboundHandler 另个一个强大的方面是它具有在请求时延迟操作或者事件的能力。比如，当你在写数据到 
remote peer 的过程中被意外暂停，你可以延迟执行刷新操作，然后在迟些时候继续*/
public class DiscardHandlerrAdapter extends ChannelOutboundHandlerAdapter {

	
}
