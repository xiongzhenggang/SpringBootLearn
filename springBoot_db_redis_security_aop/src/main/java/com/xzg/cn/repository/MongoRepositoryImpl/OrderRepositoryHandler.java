package com.xzg.cn.repository.MongoRepositoryImpl;

import com.xzg.cn.entity.Order;
import com.xzg.cn.repository.ExtreaDaoMethod.MixMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//OrderRepository接口有spring实现OrderRepositoryHander，并添加MixMongoRepository接口的实现方式
//作为混合使用.注意默认为Impl结尾，Handler需要配置
@Repository
public class OrderRepositoryHandler implements MixMongoRepository {
    @Autowired
    private MongoOperations mongo;
    @Override
    public List<Order> findOrdersByType(String t) {
        String type =  t.equals("NET")?"WEB":t;
        Criteria where = Criteria.where("type")
                .is(t);
        Query query = Query.query(where);
        return mongo.find(query,Order.class);
    }
}
