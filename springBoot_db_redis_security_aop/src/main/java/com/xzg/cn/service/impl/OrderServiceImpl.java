package com.xzg.cn.service.impl;

import com.xzg.cn.entity.Order;
import com.xzg.cn.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
//服务首先接口，缓加在接口上
@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public List<Order> findall() {
        return null;
    }
}
