//package com.xzg.cn.configure;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
////spring boot 已经实现如下配置，需要手动配置的application.properties文件
//@Configuration
//@EnableCaching//启动自动缓存
//public class RedisCacheConfig extends CachingConfigurerSupport {
////    @Value("${spring.redis.host}")
////    private String host;
////    @Value("${spring.redis.port}")
////    private int port;
////    @Value("${spring.redis.timeout}")
////    private int timeout;
////
////    @Bean
////    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
////        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
////
////        // Number of seconds before expiration. Defaults to unlimited (0)
////        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
////        return cacheManager;
////    }
////    //spring boot 自动实现工厂，不需要在手动实现
////    @Bean
////    public JedisConnectionFactory redisConnectionFactory()  {
////        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
////        // Defaults
////        redisConnectionFactory.setHostName(host);
////        redisConnectionFactory.setPort(port);
////        return redisConnectionFactory;
////    }
//@Autowired
//private JedisConnectionFactory jedisConnectionFactory;
//@Bean
//public RedisTemplate redisTemplate() {
//    StringRedisTemplate redisTemplate = new StringRedisTemplate(jedisConnectionFactory);
//
//    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//    ObjectMapper om = new ObjectMapper();
//    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//    jackson2JsonRedisSerializer.setObjectMapper(om);
//    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//    redisTemplate.afterPropertiesSet();
//    return redisTemplate;
//}
//// 当有多个缓存器中，配置多个缓存管理器，如下
//  /*  @Bean
//    public CacheManager cacheManager(net.sf.ehcache.CacheManager cm){
//        List<CacheManager> managers = new ArrayList<>();
//        managers.add(new EhCacheCacheManager(cm));
//        managers.add(new RedisCacheManager(redisTemplate());
//    }*/
//
//}
