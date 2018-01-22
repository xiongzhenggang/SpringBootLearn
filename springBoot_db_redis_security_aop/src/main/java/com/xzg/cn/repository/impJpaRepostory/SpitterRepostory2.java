package com.xzg.cn.repository.impJpaRepostory;

import com.xzg.cn.entity.Spitter;
import com.xzg.cn.repository.ExtreaDaoMethod.SpitterSweeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
//继承JpaRepository后可以直接使用其内部已实现的方法
public interface SpitterRepostory2 extends JpaRepository<Spitter,Long> ,SpitterSweeper{//为了确保eliteSweep方法在接口中
    //jpa根据方法名判断查询
    Spitter findByName(String name);
    //自定义sql查询
    //nativeQuery,使用原生的sql语句查询。使用java对象'Book'作为表名来查自然是不对的
    //@Query(value = "select s from Spitter s where s.name = :name", nativeQuery = true) 换成Spitter会报错
    @Query("select s from Spitter s where s.name = :name")//Spitter换成spitter会报错，
    List<Spitter> findSpitterByNmae(String name);
}
