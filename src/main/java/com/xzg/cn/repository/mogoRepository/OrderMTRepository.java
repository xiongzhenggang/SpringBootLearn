package com.xzg.cn.repository.mogoRepository;

import com.xzg.cn.entity.Order;

public interface OrderMTRepository {
    public void saveOrder(Order order);
    public long  countOrder();
}
