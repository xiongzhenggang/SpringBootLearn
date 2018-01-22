package com.xzg.cn.repository.mogoRepository;

import com.xzg.cn.entity.Order;
import com.xzg.cn.repository.ExtreaDaoMethod.MixMongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String>,MixMongoRepository {
    //添加额外的方法
    List<Order> findByCustomer(String c);
    //与jpa不同的是这里提供json类型的查询条件
    @Query("{'customer' : 'chuck wagon','type' : ?0}")
    List<Order> findChucksOrders(String t);

}
