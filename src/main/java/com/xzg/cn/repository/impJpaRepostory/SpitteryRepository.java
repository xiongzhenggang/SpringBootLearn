package com.xzg.cn.repository.impJpaRepostory;

import com.xzg.cn.entity.Spitter;

import java.util.List;

public interface SpitteryRepository {

    public void addSpittery(Spitter spitter);

    public Spitter getSpitter(long id);

//    public List<Spitter> getAll();
}
