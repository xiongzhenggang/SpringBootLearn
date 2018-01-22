package com.xzg.cn.hessianService;

import com.xzg.cn.entity.Spitter;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public Spitter getSpitter() {
        Spitter spitter = new Spitter();
        spitter.setName("兄弟");
        spitter.setId(1);
        return spitter;
    }
}
