package com.xzg.cn.config;

import com.xzg.cn.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

//配置stomp
@Configuration
@EnableWebSocketMessageBroker
public class StompConfig extends AbstractWebSocketMessageBrokerConfigurer {
// register your user interceptor by overriding configureClientInboundChannel
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/webSocketServer来和服务器的WebSocket连接
        stompEndpointRegistry.addEndpoint("/webSocketServer").setAllowedOrigins("*").withSockJS();
    }
    /**
     * 配置信息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称
        //enableSimpleBroker 是spring提供简单的代理，基于内存实现，如果需要更高级功能可以选择rabbit、activitie mq
//        registry.enableSimpleBroker("/queue", "/topic");
        //启用stomp代理中继 启动Rabbit stomp插件： rabbitmq-plugins enable rabbitmq_stomp
        registry.enableStompBrokerRelay("/topic","/queue")
                .setRelayHost("192.165.1.105")
                //amqp:5672 clustering:5672  http:15672
                .setRelayPort(61613)
                .setClientLogin("xzg")
                .setClientPasscode("xzg")
//                .setSystemLogin("xzg")
//                .setSystemPasscode("xzg")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         registry.setUserDestinationPrefix("/user/");
    }
}

