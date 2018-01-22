package com.xzg.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public MailSender mailSender(){
        JavaMailSenderImpl  mailSender  = new JavaMailSenderImpl();
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");//一定要引号引起来
        javaMailProperties.put("mail.smtp.starttls.enable","true");
        javaMailProperties.put("mail.smtp.timeout", "25000");
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.socketFactory.fallback", "false");
        javaMailProperties.put("mail.smtp.host", "smtp.outlook.com");
        mailSender.setHost("smtp.outlook.com");
        mailSender.setJavaMailProperties(javaMailProperties);
        mailSender.setPort(465);
        mailSender.setUsername("xxx@outlook.com");
        mailSender.setPassword("xxx");
        return mailSender;
    }

}
