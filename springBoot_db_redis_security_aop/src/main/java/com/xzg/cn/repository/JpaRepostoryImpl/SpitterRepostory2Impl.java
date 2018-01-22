package com.xzg.cn.repository.JpaRepostoryImpl;

import com.xzg.cn.repository.ExtreaDaoMethod.SpitterSweeper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

//当spring data jpa 为repository接口生成接口实现类。他会查找和接口名称相同，并且会加上Impl后缀的一个类
//如果这个类存在的话，spring data jpa 会将他的方法与jpa生成的方法合并在一起，下面为实现复杂sql
@Repository
public class SpitterRepostory2Impl implements SpitterSweeper{
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional//jpa的硬性要求，'没有事务支持，不能执行更新和删除操作'
    public int eliteSweep() {
        String update = "UPDATE Spitter spitter" +
                " SET spitter.name = 'lisi' "+
                " WHERE spitter.id = '1' ";
        return em.createQuery(update).executeUpdate();
    }
}
