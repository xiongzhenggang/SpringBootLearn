package com.xzg.nettyRpc.registry;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author xzg
 *使用 ZooKeeper 客户端可轻松实现服务注册功能，注意父节点是否存在.Zookeeper集群上注册服务地址
 *ServiceRegistry的目的是服务端调用，将自己的服务注册到zookpeeper来管理，这样客户端就可以通过zookpeeper的发现
 *功能找到需的服务端。
 */
public class ServiceRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);
	//同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	//服务端在调用注册的时候避免其他线程也正在注册导致两个不同的服务之间的误操作
    private CountDownLatch latch = new CountDownLatch(1);
    //由spring构造器注入
    private String registryAddress;
    //soring.xml已经将注册地址注入
    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
            	//判断父节点是否存在
            	AddRootNode(zk);
            	
                createNode(zk, data);
            }
        }
    }

    /**
     * @return
     * 链接zookeeper
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent event) {
                	// 	一旦客户端和服务器的某一个节点建立连接（注意，虽然集群有多个节点，但是客户端一次连接到一个节点就行了），
                	//并完成一次version、zxid的同步，这时的客户端和服务器的连接状态就是SyncConnected
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                    	//countDown方法，当前线程调用此方法，则计数减一
                        latch.countDown();
                    }
                }
            });
            try {
            	//awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    /**
     * @param zk
     * @param data
     * 创建节点
     */
    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path;
			try {
				path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				LOGGER.debug("create zookeeper node ({} => {})", path, data);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				 LOGGER.error("", e);
			}
        	} catch (KeeperException e) {
            LOGGER.error("", e);
        	}
    }
    /**
     * @param zk
     * 判断是是否存在父节点，如果不存在就重新创建
     */
    private void AddRootNode(ZooKeeper zk){
        try {
            Stat s = zk.exists(Constant.ZK_REGISTRY_PATH, false);
            if (s == null) {
                zk.create(Constant.ZK_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            LOGGER.error(e.toString());
        } catch (InterruptedException e) {
            LOGGER.error(e.toString());
        }
    }
}
