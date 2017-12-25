package com.xzg.cn.configure;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import net.sf.ehcache.CacheManager;

@Configurable
@EnableCaching//启用缓存注解
public class EhCacheConfig {
    //配置EhCacheCacheManager
    @Bean
    public EhCacheCacheManager cacheCacheManager(CacheManager cm){
        return new EhCacheCacheManager(cm);
    }
    //配置EhCacheManagerFactoryBean，ehcache.xml在resource下的配置
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
           EhCacheManagerFactoryBean ehCacheManagerFactoryBean =
                   new EhCacheManagerFactoryBean();
           ehCacheManagerFactoryBean.setConfigLocation(
                   new ClassPathResource("ehcache.xml")
           );
           return ehCacheManagerFactoryBean;
    }
}
