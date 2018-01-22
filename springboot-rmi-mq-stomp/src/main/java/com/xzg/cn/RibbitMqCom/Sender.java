package com.xzg.cn.RibbitMqCom;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String content) {
        rabbitTemplate.convertAndSend(content);
        System.out.println("发送消息: '" + content + "'");
    }

}