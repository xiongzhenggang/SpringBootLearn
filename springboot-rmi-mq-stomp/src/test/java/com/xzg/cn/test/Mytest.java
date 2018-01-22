package com.xzg.cn.test;

import com.xzg.cn.MainController;
import com.xzg.cn.RibbitMqCom.Receiver;
import com.xzg.cn.RibbitMqCom.Sender;
import com.xzg.cn.entity.Spitter;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.security.PrivateKey;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
//@SpringApplicationConfiguration(classes = SampleController.class) // 指定我们SpringBoot工程的Application启动类
//@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@SpringBootTest(classes = MainController.class)
public class Mytest {
    private final static  Logger LOGGER = Logger.getLogger(Mytest.class);
    @Resource
    private Sender sender;
    @Resource
    private Receiver receiver;
    @Autowired
    private JavaMailSender mailSender;
    //rabbit mq 测试
  /*  @Test
    public void testRibbitMq(){
        String msg = "你好啊";
        sender.sendMsg(msg);
        LOGGER.info("发送信息："+msg);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("发送完毕");
        //两秒后从队列中去取
        LOGGER.info("从rabbitMq取的信息："+receiver.getMessage());
    }*/
    @Test
    public void testEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xxx@outlook.com");
        message.setTo("xxx@outlook.com"); //自己给自己发送邮件
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
    }
}
