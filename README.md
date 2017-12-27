## spring boot 简化了很多spring操作，但是有时候除了问题也会让人一头雾水。本项目单纯对springboot
## 学习以及会对spring boot缺少的配置做相应的提示
###  springboot 启用缓存非常的简单
*  spring 使用缓存基本原理是使用aop 拦截的原理，加上相关的注解方便用户实使用，下面就springboot和spring的缓存配置做简要的介绍
```java
//首先如果spring中启用缓存的话首先需要配置相关的mananger，下面是一个基本的配置
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
```
* 当然如果使用的是springboot则不需要这么配置了，springboot会替你完成上面的工作，你只需要启用就可以了，如下面
```java
//springboot 替你完成剩下的，当然ehcache.xml等需要默认。如果自己修改可按照上面的来配置
@Configurable
@EnableCaching//启用缓存注解
public class EhCacheConfig {
}
```
* 而ehcach基本的xm配置可如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache>
    <!--切换为ehcache 缓存时使用,声明一个名为pittleCache 最大堆内存为50M 存货时间为100秒的缓存-->
    <cache name="spittleCache" maxBytesLocalHeap="50m"
                        timeToLiveSecond="100">
    </cache>
</ehcache>
```
### redis 作为缓存和ehcache配置类似，暂不叙说，配置文件路径com.xzg.cn.configure可自行查看
* springboot 已经实现相关配置，需要手动配置的是application.properties文件
### 下面使用过程，则通过几种注解实现
```
@Cacheable ：表明spring在调用方法之前，首先在缓存查找缓存，如果找到直接返回
否则嗲用方法返回值，并保存到缓存中
@CachePut : 始终调用方法，并返回值放入缓存中
@CacheEvict ： 清除spring缓存中的一个或多个条目
@CacheConfig : 这是一个分组注解，能够同时应用多个其他的缓存注解
```
## 需要注意的是，@EnableCaching需要加到SpringbootApplication启动类上，否则缓存不会起作用
