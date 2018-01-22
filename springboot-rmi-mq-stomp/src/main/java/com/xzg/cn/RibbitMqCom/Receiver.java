package com.xzg.cn.RibbitMqCom;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @RabbitListener(queues = "myQueue") 注解，指定从 myQueue 这个队列中获取消息。
   /* @RabbitListener(queues = "myQueue")
    public void processMessage(Message message) {
        byte[] body = message.getBody();
        System.out.println("收到消息: '" + new String(body) + "'");
    }*/
    public String getMessage(){
        return (String)rabbitTemplate.receiveAndConvert("myQueue");
    }
}