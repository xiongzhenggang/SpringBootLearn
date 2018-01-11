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
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
//使用spring-data-redis
@Bean
public RedisClusterConfiguration redisClusterConfiguration(){
    RedisClusterConfiguration rc = new RedisClusterConfiguration();
    Set<RedisNode> jedisClusterNodes = new HashSet<RedisNode>();
    // Jedis Cluster will attempt to discover cluster nodes automatically
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7000));
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7001));
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7002));
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7003));
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7004));
    jedisClusterNodes.add(new RedisNode("192.168.1.105", 7005));
    rc.setClusterNodes(jedisClusterNodes);
    return  rc;
}
//添加集群配置以及redis池
    @Bean
    public JedisConnectionFactory redisConnectionFactory()  {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration(),
                jedisPoolConfig());
        return redisConnectionFactory;
    }
//直接使用rredis集群的客户端，与spring-data-redis无关了
@Bean
public JedisCluster jedisCluster(){
    Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
    nodes.add(new HostAndPort("192.168.1.105", 7000));
    nodes.add(new HostAndPort("192.168.1.105", 7001));
    nodes.add(new HostAndPort("192.168.1.105", 7002));
    nodes.add(new HostAndPort("192.168.1.105", 7003));
    nodes.add(new HostAndPort("192.168.1.105", 7004));
    nodes.add(new HostAndPort("192.168.1.105", 7005));
   /* String[] hosts = {"192.168.1.105:7000",
            "192.168.1.105:7001",
            "192.168.1.105:7002",
            "192.168.1.105:7003",
            "192.168.1.105:7004",
            "192.168.1.105:7005"};
    Set<HostAndPort> nodes = new HashSet<>();
    for (String hn : hosts) {
        String[] ipPort = hn.split(":");
        nodes.add(new HostAndPort(ipPort[0].trim(),Integer.valueOf(ipPort[1].trim())));
    }*/
    return new JedisCluster(nodes, jedisPoolConfig());
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
//哨兵模式弃用，使用集群模式替代
/*public JedisSentinelPool jedisSentinelPool() {
    String[] hosts = {"192.168.1.105:26379","192.168.1.105:36379","192.168.1.105:46379"};
    HashSet<String> sentinelHostAndPorts = new HashSet<>();
    for (String hn : hosts) {
        sentinelHostAndPorts.add(hn);
    }
    JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinelHostAndPorts, jedisPoolConfig());
    *//*sentinelPool.setMaxTotal(10);
        jedisPoolConfig.setMaxWaitMillis(1000);*//*
        return sentinelPool;
    }*/
   /* //哨兵
    public RedisSentinelConfiguration jedisSentinelConfig() {
        String[] hosts = {"192.168.1.105:26379","192.168.1.105:36379","192.168.1.105:46379"};
        HashSet<String> sentinelHostAndPorts = new HashSet<>();
        for (String hn : hosts) {
            sentinelHostAndPorts.add(hn);
        }
        System.out.println(host);
        return new RedisSentinelConfiguration("mymaster", sentinelHostAndPorts);

    }*/
}
