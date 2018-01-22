package com.xzg.cn.repository.MongoRepositoryImpl;

import com.xzg.cn.entity.Order;
import com.xzg.cn.repository.mogoRepository.OrderMTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class OrderMTRepositoryImpl implements OrderMTRepository {
    @Autowired
    //MongoTemplate 已经注入到MongoOperations属性中了.MongoOperation是MongoTemplate具体实现了
    private MongoOperations mongo;

    @Override
    public void saveOrder(Order order) {
        mongo.save(order);
    }

    @Override
    public long countOrder() {
       return  mongo.getCollection("order").count();
    }
}

