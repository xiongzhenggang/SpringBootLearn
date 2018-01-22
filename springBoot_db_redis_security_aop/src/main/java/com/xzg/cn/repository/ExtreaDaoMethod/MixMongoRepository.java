package com.xzg.cn.repository.ExtreaDaoMethod;

import com.xzg.cn.entity.Order;

import java.util.List;
//mongodb混合使用的中间接口
public interface MixMongoRepository {
    List<Order> findOrdersByType(String t);
}
