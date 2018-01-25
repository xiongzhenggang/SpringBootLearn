package com.xzg.cn.webstomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
//控制层
@Controller
public class WebSocketController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //  @MessageMapping类似@RequestMapping
    @MessageMapping("/sendTest")
    //@SendTo 注解表示处理对应请求后要返回订阅的地址，若没有添加@SendTo注解返回的地址为（/topic/sendTest）
    @SendTo("/topic/subscribeTest")
    public ServerMessage sendDemo(ClientMessage message) {
        logger.info("接收到了信息" + message.getName());
        return new ServerMessage("你发送的消息为:" + message.getName());
    }

    //与@MessageMapping不同的是@SubscribeMapping会直接将请求结果发送给客户端而不经过代理
    @SubscribeMapping("/subscribeTest")
    public ServerMessage sub() {
        logger.info("XXX用户订阅了我。。。");
        return new ServerMessage("感谢你订阅了我。。。");
    }

}
