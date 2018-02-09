package com.xzg.nettyRpc.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzg.nettyRpc.protocol.RpcRequest;
import com.xzg.nettyRpc.protocol.RpcResponse;
/**
 * @author xzg
 * RpcHandler中处理 RPC 请求，只需扩展 Netty 的SimpleChannelInboundHandler抽象类即可
 * SimpleChannelInboundHandler常见的处理器是接收到解码后的消息并应用一些业务逻辑到这些数据
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

   /* @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
        	//动态代理获取目标对象的代理对象
            Object result = handle(request);
            //封装到responces中返回到客户端
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }*/
    @Override
    public void channelRead0(final ChannelHandlerContext ctx,final RpcRequest request) throws Exception {
        RpcServer.submit(() -> {
        	 LOGGER.debug("Receive request " + request.getRequestId());
             RpcResponse response = new RpcResponse();
             response.setRequestId(request.getRequestId());
             try {
                 Object result = handle(request);
                 response.setResult(result);
             } catch (Throwable t) {
                 response.setError(t);
                 LOGGER.error("RPC Server handle request error",t);
             }
             ctx.writeAndFlush(response)
             	.addListener(ChannelFutureListener.CLOSE)
             	.addListener(new ChannelFutureListener() {
             			public void operationComplete(ChannelFuture channelFuture) throws Exception {
             				LOGGER.debug("Send response for request " + request.getRequestId());
             			}
             	});
        	});
    }
    /**
     * @param request
     * @return
     * @throws Throwable
     * 
     */
    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);

        Class<?> serviceClass = serviceBean.getClass();
        
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        
        //也可使用java中的普通代理实现
        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/
        //使用cglib
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    //处理异常信息
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("server caught exception", cause);
        ctx.close();
    }
}
