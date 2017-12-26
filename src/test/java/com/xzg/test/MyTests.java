package com.xzg.test;

import com.xzg.cn.aop.TrackCounter;
import com.xzg.cn.common.BlankDisc;
import com.xzg.cn.common.BlankDisc2;
import com.xzg.cn.common.CompactDisc;
import com.xzg.cn.common.ExpressiveConfig;
import com.xzg.cn.entity.Spitter;
import com.xzg.cn.repository.impJpaRepostory.SpitterRepostory2;
import com.xzg.cn.repository.impJpaRepostory.SpitteryRepository;
import com.xzg.cn.repository.mogoRepository.OrderMTRepository;
import com.xzg.cn.repository.mogoRepository.OrderRepository;
import com.xzg.cn.service.SpitterService;
import hello.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
//@SpringApplicationConfiguration(classes = SampleController.class) // 指定我们SpringBoot工程的Application启动类
//@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@SpringBootTest(classes = SampleController.class)
public class MyTests {

    @Resource
    private ExpressiveConfig ex;

    @Autowired
    private BlankDisc2 bl2;

    @Autowired
    private TrackCounter tc;

    @Autowired
    private SpitteryRepository sR;

    @Autowired
    private CompactDisc cd;
    @Autowired
    private BlankDisc blankDisc;
    @Autowired
    private SpitterRepostory2 spitterRepostory2;
    //测试mongodb
    @Autowired
    private OrderMTRepository orderMTRepository;
    //注入混合类
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SpitterService spitterService;
    @Test
    public void exampleTest() {
//        System.out.println(bl2.toString());
//        System.out.println(ex.disc().toString());
/*            cd.playTranks(1);
            cd.playTranks(2);
            cd.playTranks(4);
            cd.playTranks(3);
            cd.playTranks(3);
            assertEquals(1,tc.getPlayCount(2 ));
//            assertEquals(1,tc.getPlayCount(3 ));
            assertEquals(2,tc.getPlayCount(3 ));*/
//        System.out.println(blankDisc.toString());
//        Encoreable ec = (Encoreable)blankDisc;
//        ((Encoreable) blankDisc).performEncore();
        //使用手动db
        /*Spitter sp = new Spitter(2,"wxiaowang");
        sR.addSpittery(sp);
        System.out.println(sR.getSpitter(2));*/
        //使用自动jpa
        /*System.out.println(spitterRepostory2.findByName("zhangsan"));
        System.out.println(spitterRepostory2.eliteSweep());*/
        //mongodb操作
      /*  Order order = new Order();
        order.setCustomer("客户段");
        order.setType("WEB");
        Item item = new Item("豆子",2,3);
        order.setItems(Arrays.asList(item));
        orderMTRepository.saveOrder(order);
        System.out.println(orderMTRepository.countOrder());
        orderRepository.findAll()
                .stream()
                .forEach(s -> {
                    System.out.println(s.toString());
                });*/
      //检测缓存是否生效
        Spitter sp = new Spitter(2,"wxiaowang");
        spitterService.addSpitter(sp);
        spitterService.getSpitterById(2);
    }

}
