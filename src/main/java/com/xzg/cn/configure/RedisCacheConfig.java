package com.xzg.cn.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

//spring boot 已经实现如下配置，需要手动配置的application.properties文件
@Configuration
@EnableCaching//启动自动缓存
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;
   /* @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager =  RedisCacheManager.create(redisConnectionFactory());
        // Number of seconds before expiration. Defaults to unlimited (0)
//        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        cacheManager.setTransactionAware(true);
        return cacheManager;
    }*/
    //spring boot 自动实现工厂，不需要在手动实现
public JedisPoolConfig jedisPoolConfig() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(10);
    jedisPoolConfig.setMaxTotal(10);
    jedisPoolConfig.setMaxWaitMillis(1000);
    return jedisPoolConfig;

}
    public RedisSentinelConfiguration jedisSentinelConfig() {
        String[] hosts = {"192.168.1.105","192.168.1.106"};
        HashSet<String> sentinelHostAndPorts = new HashSet<>();
        for (String hn : hosts) {
            sentinelHostAndPorts.add(hn);
        }
        System.out.println(host);
        return new RedisSentinelConfiguration(host, sentinelHostAndPorts);

    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory()  {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(jedisSentinelConfig(),
                jedisPoolConfig());
       /* if (!StringUtils.isEmpty("123456"))
            redisConnectionFactory.setPassword(redisProperties.getPassword());
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        // Defaults
        redisConnectionFactory.setHostName(host);
        redisConnectionFactory.setPort(port);*/
        return redisConnectionFactory;
    }
@Bean
public RedisTemplate redisTemplate() {
    StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory());

    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
}
// 当有多个缓存器中，配置多个缓存管理器，如下

}
