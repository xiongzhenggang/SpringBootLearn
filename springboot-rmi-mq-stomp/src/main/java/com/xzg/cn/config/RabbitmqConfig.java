package com.xzg.cn.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String EXCHANGE   = "spring-boot-exchange";
    //链接工厂
    @Bean
    CachingConnectionFactory myConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername("xzg");
        connectionFactory.setPassword("xzg");
        connectionFactory.setHost("192.168.1.105");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }
   /* @Bean
    Exchange myExchange() {
        return ExchangeBuilder.topicExchange("test.topic").durable().build();
    }*/
//   Exchange 生产者将消息发送到 Exchange，由 Exchange 根据一定的规则将消息路由到一个或多个 Queue 中（或者丢弃
    @Bean
    TopicExchange exchange() {
     /*
        Exchange Types RabbitMQ常用的Exchange Type有 fanout、 direct、 topic、 headers 这四种。
        fanout 这种类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中，这时 Routing key 不起作用。
        direct 这种类型的Exchange路由规则也很简单，它会把消息路由到那些 binding key 与 routing key完全匹配的Queue中。
        topic 这种类型的Exchange的路由规则支持 binding key 和 routing key 的模糊匹配，会把消息路由到满足条件的Queue。 binding key 中可以存在两种特殊字符 *与 #，用于做模糊匹配，其中 * 用于匹配一个单词，# 用于匹配多个单词（可以是零个），单词以 .为分隔符。
        headers 这种类型的Exchange不依赖于 routing key 与 binding key 的匹配规则来路由消息，而是根据发送的消息内容中的 headers 属性进行匹配。
*/
        return new TopicExchange(EXCHANGE);
    }
//    Queue 是RabbitMQ的内部对象，用于存储消息。RabbitMQ中的消息只能存储在 Queue 中，消费者从 Queue 中获取消息并消费
    @Bean
    Queue myQueue() {
        return QueueBuilder.durable("myQueue").build();
    }
//    Binding RabbitMQ中通过 Binding 将 Exchange 与 Queue 关联起来。
    @Bean
    public Binding myExchangeBinding(@Qualifier("exchange") Exchange topicExchange,
                                     @Qualifier("myQueue") Queue queue) {
//      binding key 中可以存在两种特殊字符 *与 #，用于做模糊匹配，
//      其中 * 用于匹配一个单词，# 用于匹配多个单词（可以是零个），单词以 .为分隔符
        return BindingBuilder.bind(queue).to(topicExchange).with("spring.#").noargs();
    }
    /*@Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }*/

    @Bean
    public RabbitTemplate myExchangeTemplate(CachingConnectionFactory myConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(myConnectionFactory);
        //要和rabbit服务端一致
        rabbitTemplate.setExchange(EXCHANGE);
        rabbitTemplate.setRoutingKey("spring.abc.123");
        rabbitTemplate.setQueue("myQueue");
        return rabbitTemplate;
    }
}
