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
### 添加redis缓存，这里使用的redis集群，单机不赘述
[redis集群搭建](https://github.com/xiongzhenggang/xiongzhenggang.github.io/blob/master/java%E6%A1%86%E6%9E%B6/redis/redis-%E5%AE%98%E6%96%B9cluster%E6%90%AD%E5%BB%BA%E7%AD%96%E7%95%A5.md)
* 配置类如下：
```java
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
    return new JedisCluster(nodes, jedisPoolConfig());
}
}
```
* 上述中的集群主机需要配置好
可以通过测试类测试Mytest.java
```java
        spitterService.addSpitter(sp);
//spitterService中的实现类中，添加了注解缓存
        System.out.println(spitterService.getSpitterById(2));
        System.out.println(spitterService.getSpitterById(2));
```
spitterService的实现类中
```java
    @Override
    //取出 （key="#p0"）指定传入的第一个参数作为redis的key
    @Cacheable(value = "spittleCache" , key = "#id")
    public Spitter getSpitterById(long id) {
        System.out.println("=====》数据库执行前");
        Spitter sp = sR.getSpitter(id);
        System.out.println("=====》数据库执行后");
        return sp;
    }
}
```
测试结果：
```xml
Hibernate: insert into spitter (email, name, id) values (?, ?, ?)
=====》数据库执行前
Hibernate: select spitter0_.id as id1_0_0_, spitter0_.email as email2_0_0_, spitter0_.name as name3_0_0_ from spitter spitter0_ where spitter0_.id=?
=====》数据库执行后
Spitter{id=2, name='wxiaowang'}
Spitter{id=2, name='wxiaowang'}
```
可以看到两次执行，只查询了一次数据库

### rmi hessian httpinvoker 等spring配置使用
* 以上几种均属于rmi。如果直接使用的话会比较负载，这里使用spring
* 配置俩简化。以hessian和httpInvoker为例
```
所有的远程调用服务（客户端调用服务端腹部的服务）
```
1.  服务端配置发布服务的使用（需实现发布服务的接口及实现类）

1） 
```java
//Hessian服务端配置
@Configuration
public class HessianConfig {
    //不要让spring security拦截
    @Bean(name = "/HelloWorldService")
    //HessianServiceExporter是一个spring mvc的控制器，他接受Hessian请求，并将这些请求转换成
    //对到处pojo的方法调用。HessianServiceExporter会把helloService bean到处为Heassian服务
    public HessianServiceExporter hessianHelloServiceExporter(HelloService helloService){
       //HelloService 为服务端接口、且有对应的实现类
        HessianServiceExporter export = new HessianServiceExporter();
        export.setService(helloService);
        export.setServiceInterface(HelloService.class);
        return export;
    }
}

//配置httpInvoker
@Configuration
public class HttpInvokerConfig {
    //HttpInvokerServiceExporter 是一个sprinvmvc的控制器，需要url映射
    @Bean
    public HttpInvokerServiceExporter httpExporterHelloService(HelloService helloService){
        HttpInvokerServiceExporter exporter =
                new HttpInvokerServiceExporter();
        exporter.setService(helloService);
        exporter.setServiceInterface(HelloService.class);
        return exporter;
    }
    //需要url映射
    @Bean
    public HandlerMapping handlerMapping(){
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        //httpExporterHelloService 注入上面的bean
        mappings.setProperty("/hello.service","httpExporterHelloService");
        mapping.setMappings(mappings);
        return mapping;
    }
}
```

2. Hessian客户端不使用用Spring的使用过程（客户端需要定义与服务端相同的接口）
```java
//测试不适用spring的Hessian客户端使用
public class ClientHessian {
    public static void main(String[] args) {
                String url = "http://localhost:8080/HelloWorldService";

                try {
                    HessianProxyFactory  factory = new HessianProxyFactory();
                    factory.setOverloadEnabled(true);
                    HelloService service = (HelloService) (factory).create(HelloService.class, url);
                        System.out.println(service.getSpitter());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
           }
}
```
* httpInvoker 客户端spring配置
```java
@Configuration
public class HttpInvokerClientConfig {
    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl("http://localhost:8080/Hello/hello.service/");
        proxy.setServiceInterface(HelloService.class);
        return proxy;
    }
    //然后就可以直接使用@Autowired注入HelloService使用
}
//类似与@Autowired
// private HelloService helloService; 直接使用
```
