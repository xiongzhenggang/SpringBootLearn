package com.xzg.cn.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

//spring boot 已经实现如下配置，需要手动配置的application.properties文件
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 2)
@Configuration
@EnableCaching//启动自动缓存
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
//@SuppressWarnings("rawtypes") RedisTemplate redisTemplate
    @Bean
    public CacheManager redisCacheManager() {
        RedisCacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory());
        // Number of seconds before expiration. Defaults to unlimited (0)
//        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        cacheManager.setTransactionAware(true);
        return cacheManager;
    }
    // 集群 spring boot 自动实现工厂，不需要在手动实现@
public JedisPoolConfig jedisPoolConfig() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxIdle(10);
    jedisPoolConfig.setMaxTotal(10);
    jedisPoolConfig.setMaxWaitMillis(1000);
    return jedisPoolConfig;
}
//哨兵+集群
public JedisSentinelPool jedisSentinelPool() {
    String[] hosts = {"192.168.1.105:26379"};
    HashSet<String> sentinelHostAndPorts = new HashSet<>();
    for (String hn : hosts) {
        sentinelHostAndPorts.add(hn);
    }
    JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinelHostAndPorts, jedisPoolConfig());
    /*sentinelPool.setMaxTotal(10);
        jedisPoolConfig.setMaxWaitMillis(1000);*/
        return sentinelPool;
    }
    //哨兵
    public RedisSentinelConfiguration jedisSentinelConfig() {
        String[] hosts = {"192.168.1.105:26379","192.168.1.105:36379","192.168.1.105:46379"};
        HashSet<String> sentinelHostAndPorts = new HashSet<>();
        for (String hn : hosts) {
            sentinelHostAndPorts.add(hn);
        }
        System.out.println(host);
        return new RedisSentinelConfiguration("mymaster", sentinelHostAndPorts);

    }
    @Bean
    public JedisConnectionFactory redisConnectionFactory()  {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(jedisSentinelConfig(),
                jedisPoolConfig());
        //RedisStandaloneConfiguration standaloneConfig, JedisClientConfiguration clientConfig
       /* if (!StringUtils.isEmpty("123456"))
            redisConnectionFactory.setPassword(redisProperties.getPassword());
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        // Defaults
        redisConnectionFactory.setHostName(host);
        redisConnectionFactory.setPort(port);*/
        return redisConnectionFactory;
    }
/*@Bean
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
}*/
//注入客户端，与缓存无关
@Bean
public JedisCluster jedisCluster(){
    String[] hosts = {"192.168.1.105:26379","192.168.1.105:36379","192.168.1.105:46379"};
    Set<HostAndPort> nodes = new HashSet<>();
    for (String hn : hosts) {
        String[] ipPort = hn.split(":");
        nodes.add(new HostAndPort(ipPort[0].trim(),Integer.valueOf(ipPort[1].trim())));
    }
    return new JedisCluster(nodes, jedisPoolConfig());
}

}
