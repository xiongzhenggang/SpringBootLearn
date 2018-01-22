package com.xzg.cn.service.impl;

import com.xzg.cn.entity.Spitter;
import com.xzg.cn.repository.impJpaRepostory.SpitteryRepository;
import com.xzg.cn.service.SpitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
//服务实现接口
@Service
public class SpitterServiceImpl implements SpitterService {
    //这里是dao接口实现
    @Autowired
    private SpitteryRepository sR;

    @Override
    //见spitter的id作为缓存的key
//    @CachePut(value = "spittleCache",key = "#result.id")
    public void addSpitter(Spitter spitter) {
        sR.addSpittery(spitter);
    }

    @Override
    //取出 （key="#p0"）指定传入的第一个参数作为redis的key
    @Cacheable(value = "spittleCache" , key = "#id")//spittleCache
    public Spitter getSpitterById(long id) {
        System.out.println("=====》数据库执行前");
        Spitter sp = sR.getSpitter(id);
        System.out.println("=====》数据库执行后");
        return sp;
    }
}
