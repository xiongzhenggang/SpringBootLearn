package com.xzg.zuul.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableCircuitBreaker
//@EnableResourceServer
@EnableFeignClients
@ComponentScan({"com.xzg.zuul.server", "com.xzg.common"})
public class EdgeApp {
    private static final Logger LOG = LoggerFactory.getLogger(EdgeApp.class);

    static {
        // 本地测试在创建 SSL 连接时，HttpsClient 步骤并进行基本的服务器身份验证，以防止 URL 欺骗，其中包括验证服务器的名称是否在证书中
        LOG.warn("禁用ssl主机名检查，开发截断使用");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
//mq 在后续链路追踪时使用UserInfoTokenServices
  /*  @Value("${app.rabbitmq.host:localhost}")
    String rabbitMqHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        LOG.info("Create RabbitMqCF for host: {}", rabbitMqHost);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqHost);
        return connectionFactory;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(EdgeApp.class, args);
    }
}






