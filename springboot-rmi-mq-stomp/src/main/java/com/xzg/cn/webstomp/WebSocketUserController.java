package com.xzg.cn.webstomp;

import com.xzg.cn.entity.Spitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.Notification;
import java.security.Principal;

//控制层，首先基于spring的简单代理（内存）
@Controller
public class WebSocketUserController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
    @RequestMapping(value = "serveruUserSendInfo",method = RequestMethod.GET)
    @ResponseBody
    public Spitter templateTest() {
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推到subscribeTest的数据"));
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/sendTest", new ServerMessage("服务器主动推到sendTest的数据"));
        return new Spitter(2,"dd");
    }

    //测试对制定的用户发送推送
    //@MessageMapping和@SubscricbeMapping 标注的方法能够使用Principal来获取认证用户
    //@MessageMapping和@SubscricbeMapping @MessageExcepti标注的方法返回的值能够以消息的形式发送个认证用户
    //SimpMessageingTemplate能够发送给特定用户

    @MessageMapping("/spittle")
    @SendToUser("/queue/notifications")
    public ServerMessage handleSpittle(ClientMessage message){
        Spitter sp = new Spitter(4,message.getName());
        //一些其他操作
        System.out.println("发来的消息："+message.getName());
        return new ServerMessage("发给指定用户："+message.getName());
    }

    @RequestMapping(value = "sendAssageUser",method = RequestMethod.GET)
    @ResponseBody
    public void sendAssageUser(ClientMessage message) {
//        messagingTemplate.convertAndSend("/topic/spittlefeed","发给订阅topic/spittlefeed的");
        //下面的地址的目的地会变成/user/zhangsan/queue/notifications
        messagingTemplate.convertAndSendToUser(message.getName(),"/queue/notifications"
                ,new ServerMessage("主动发送给指定的用户"+message.getName()));
    }
}
