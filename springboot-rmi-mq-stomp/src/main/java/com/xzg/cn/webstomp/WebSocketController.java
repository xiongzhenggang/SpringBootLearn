package com.xzg.cn.webstomp;

import com.xzg.cn.entity.Spitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//控制层
@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //  @MessageMapping类似@RequestMapping
    @MessageMapping("/sendTest")
    //@SendTo 注解表示处理对应请求后要返回订阅的地址，若没有添加@SendTo注解返回的地址为（/topic/sendTest）
    @SendTo("/topic/subscribeTest")
    public ServerMessage sendDemo(ClientMessage message) {
        logger.info("接收到了信息：" + message.getName());
        return new ServerMessage("你发送的消息为:" + message.getName());
    }

    //与@MessageMapping不同的是@SubscribeMapping会直接将请求结果发送给客户端而不经过代理，直接将结果返回给发送的客户端
    @SubscribeMapping("/subscribeTest")
    public ServerMessage sub() {
        logger.info("XXX用户订阅了我。。。");
        return new ServerMessage("感谢你订阅了我。。。");
    }

    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
    @RequestMapping(value = "serverSendInfo",method = RequestMethod.GET)
    @ResponseBody
    public Spitter templateTest() {
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推到subscribeTest的数据"));
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/sendTest", new ServerMessage("服务器主动推到sendTest的数据"));
        return new Spitter(2,"推送所有订阅的用户");
    }

}
