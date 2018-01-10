package com.xzg.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class RedisSentinelDemo {

    // redis 采用1主1从方式， 主：192.168.136.144 从：192.168.136.155
    // sentinel 采用3哨兵， 144上部署2个，155上部署1个
    public static void main(String[] args) {
        // 创建哨兵池
        Set sentinels = new HashSet();
//        sentinels.add(new HostAndPort("192.168.1.105", 26379).toString());
        sentinels.add(new HostAndPort("192.168.1.105", 46379).toString());
        sentinels.add(new HostAndPort("192.168.1.105", 36379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster",
                sentinels);
        System.out.println("Current master: "
                + sentinelPool.getCurrentHostMaster().toString());

        Jedis master = sentinelPool.getResource();
        master.set("username1", "cwqsolo");
        System.out.println("set->username1:cwqsolo "+master.get("foo"));
//        sentinelPool.returnResource(master);

        // 这里休眠30秒 ,将144 主redis杀掉，按哨兵机制，将发现主redis状态down
        // 重新选举新的slave为主master
        /*try {
            System.out.println("sleep 30s  begin");
            Thread.sleep(30000);
            System.out.println("sleep 30s  end!!!");
        } catch (Exception Exc) {

            Exc.printStackTrace();
            System.exit(0);
        }*/
       /* // 重新获得jedis
        Jedis master2 = sentinelPool.getResource();
        String value = master2.get("username1");
        System.out.println("get->username1: " + value);

        master2.set("username2", "newland");
        System.out.println("set->username2:newland ");

        String value2 = master2.get("username2");
        System.out.println("get->username2: " + value2);

        master2.close();*/
        master.close();
        sentinelPool.destroy();
    }
}
