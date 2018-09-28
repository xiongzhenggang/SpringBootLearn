package com.xzg.security.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xzg
 */
@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableAuthorizationServer
@ComponentScan("com.xzg.security.service")
public class SecurityApp {
public static void main(String[] args) {
    SpringApplication.run(SecurityApp.class, args);
}
}
