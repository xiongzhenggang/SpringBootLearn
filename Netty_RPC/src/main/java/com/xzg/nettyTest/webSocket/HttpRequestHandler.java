/**
 * 
 */
package com.xzg.nettyTest.webSocket;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

/**
 * @author xzg
 *下面就是这个 HttpRequestHandler 的代码,它是一个用来处理 FullHttpRequest 
消息的 ChannelInboundHandler 的实现类。注意看它是怎么实现忽略符合 "/ws" 格式的 URI 请求的
HttpRequestHandler 做了下面几件事，
    如果该 HTTP 请求被发送到URI “/ws”，则调用 FullHttpRequest 上的 retain()，并通过调用 
    fireChannelRead(msg) 转发到下一个 ChannelInboundHandler。retain() 的调用是必要的，
    因为 channelRead() 完成后，它会调用 FullHttpRequest 上的 release() 来释放其资源。 
    （请参考我们先前在第6章中关于 SimpleChannelInboundHandler 的讨论）
    如果客户端发送的 HTTP 1.1 头是“Expect: 100-continue” ，则发送“100 Continue”的响应。
    在 头被设置后，写一个 HttpResponse 返回给客户端。注意，这不是 FullHttpResponse，这只是响应的第一部分。
    另外，这里我们也不使用 writeAndFlush()， 这个是在留在最后完成。
    如果传输过程既没有要求加密也没有要求压缩，那么把 index.html 的内容存储在一个 DefaultFileRegion 
    里就可以达到最好的效率。这将利用零拷贝来执行传输。出于这个原因，我们要检查 ChannelPipeline 中是否有一个 
    SslHandler。如果是的话，我们就使用 ChunkedNioFile。
    写 LastHttpContent 来标记响应的结束，并终止它
    如果不要求 keepalive ，添加 ChannelFutureListener 到 ChannelFuture 对象的最后写入，并关闭连接。
    注意，这里我们调用 writeAndFlush() 来刷新所有以前写的信息。
这里展示了应用程序的第一部分，用来处理纯的 HTTP 请求和响应。接下来我们将处理 WebSocket 的 frame（帧），用来发送聊天消息
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {    
	//1扩展 SimpleChannelInboundHandler 用于处理 FullHttpRequest信息,不需要显示的释放资源
	    private final String wsUri;
	    private static final File INDEX;
	  private  static Logger logger = Logger.getLogger(HttpRequestHandler.class);
	    
	    static {
	    	PropertyConfigurator.configure( "/home/xzg/java/workspace/netty_chat/src/log4j.properties");  
	        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
	        try {
	            String path = location.toURI() + "index.html";
	            System.out.println("文件路径path："+path);
	            path = !path.contains("file:") ? path : path.substring(5);
	            INDEX = new File(path);
	        } catch (URISyntaxException e) {
	            throw new IllegalStateException("Unable to locate index.html", e);
	        }
	    }
	    public HttpRequestHandler(String wsUri) {
	        this.wsUri = wsUri;
	    }
	    @Override
	    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
	    	logger.info("request.getUri"+request.getUri());//log
	    	if (wsUri.equalsIgnoreCase(request.getUri())) {
//2.如果请求是一次升级了的 WebSocket 请求，则递增引用计数器（retain）并且将它传递给在ChannelPipeline 中的下个 ChannelInboundHandler  
	            ctx.fireChannelRead(request.retain()); 
	        } else {//其他的http请求
/*http 100-continue用于客户端在发送POST数据给服务器前，征询服务器情况，看服务器是否处理POST的数据，如果不处理，
客户端则不上传POST数据，如果处理，则POST上传数据。在现实应用中，通过在POST大数据时，才会使用100-continue协议。
2、客户端策略。
　　　　1）如果客户端有POST数据要上传，可以考虑使用100-continue协议。加入头{"Expect":"100-continue"}
　　　　2）如果没有POST数据，不能使用100-continue协议，因为这会让服务端造成误解。
　　　　3）并不是所有的Server都会正确实现100-continue协议，如果Client发送Expect:100-continue消息后，在timeout时间内无响应，Client需要立马上传POST数据。
　　　　4）有些Server会错误实现100-continue协议，在不需要此协议时返回100，此时客户端应该忽略。
　　3、服务端策略。
　　　　1）正确情况下，收到请求后，返回100或错误码。
　　　　2）如果在发送100-continue前收到了POST数据（客户端提前发送POST数据），则不发送100响应码(略去)。*/
	            if (HttpHeaders.is100ContinueExpected(request)) {
	                send100Continue(ctx);        //3处理符合 HTTP 1.1的 "100 Continue" 请求
	            }
	            RandomAccessFile file = new RandomAccessFile(INDEX, "r");//4.读取 index.html
	          //组织响应报文
	            HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
	            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
	            //检查客户端是否存活
	            boolean keepAlive = HttpHeaders.isKeepAlive(request);
	            if (keepAlive) {                  //5判断 keepalive 是否在请求头里面
	                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());//Content-length：用于描述HTTP消息实体的传输长度
	                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);//设置链接状态可达
	            }
	            ctx.write(response);      //6写 HttpResponse 到客户端
	            if (ctx.pipeline().get(SslHandler.class) == null) {//判断是否需要加密 
	 //7写 index.html 到客户端，根据 ChannelPipeline 中是否有 SslHandler 来决定使用 DefaultFileRegion 还是 ChunkedNioFile
	                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));//实现零拷贝
	            } else {
	                ctx.write(new ChunkedNioFile(file.getChannel()));
	            }
	          //8.写并刷新 LastHttpContent 到客户端，标记响应完成
	            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
	            if (!keepAlive) {
	                future.addListener(ChannelFutureListener.CLOSE);//9.如果 请求头中不包含 keepalive，当写完成时，关闭 Channel
	            }
	        }
	    }
	    /**返回response的状态也是HttpVersion.HTTP_1_1*/
	    private static void send100Continue(ChannelHandlerContext ctx) {
	        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
	        ctx.writeAndFlush(response);
	    }
	    /**异常处理*/	    
	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	            throws Exception {
	        cause.printStackTrace();
	        ctx.close();
	    }
	}
