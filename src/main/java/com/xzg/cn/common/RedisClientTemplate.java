package com.xzg.cn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisClientTemplate {
    @Autowired
    private JedisCluster jedisCluster;
public void  setToRedis(String key,String value){
    try {
        jedisCluster.set(key,value);
    }catch (Exception e){
        e.printStackTrace();
    }
}
}
