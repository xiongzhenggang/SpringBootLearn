package com.xzg.cn.repository.impJpaRepostory;

import com.xzg.cn.entity.Spitter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;

@CacheConfig(cacheNames = "spittleCache")//@enableCache才有用
public interface SpitteryRepository {

    @CachePut(value = "spittleCache")
    public void addSpittery(Spitter spitter);

//    @Cacheable(value = "spittleCache",key = "#result.id")
    public Spitter getSpitter(long id);

//    public List<Spitter> getAll();
}
