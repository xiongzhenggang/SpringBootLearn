package com.xzg.cn.service;

import com.xzg.cn.entity.Order;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface OrderService {
    @Cacheable
    public List<Order> findall();

}
