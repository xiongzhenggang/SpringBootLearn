package com.xzg.cn.repository.JpaRepostoryImpl;

import com.xzg.cn.entity.Spitter;
import com.xzg.cn.repository.impJpaRepostory.SpitteryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository(value = "spitteryRepository")
@Transactional
public class JpaSpitterRepository implements SpitteryRepository {

    @PersistenceContext//entyteMannger 是非线程安全，直接注入会有安全问题。 @PersistenceContext注解他没有、
    //真正的将entyteMannger注入到容器，而是个体他一个代理。真正的entyteMannger是与当前事物相关的哪一个，
    // 如果没有就新建一个。  注意：PersistenceContext并不是spring的所以需要配置注册识别
    private EntityManager em;
    @Override
    public void addSpittery(Spitter spitter) {
        em.persist(spitter);
    }

    @Override
    public Spitter getSpitter(long id) {
        return em.find(Spitter.class,id);
    }

}
