package com.xzg.nettyRpc.registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author xzg
 *	使用 ZooKeeper 实现服务发现功能。
 *	其主要功能是客户端调用通过zookpeeper来找到可用的服务端地址
 */
public class ServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscovery.class);
    //作为多线程竞争的锁
    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<String>();

    private String registryAddress;
    //构造器初始化
    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
        //链接zookpeeper服务
        ZooKeeper zk = connectServer();
        //将所有字节点的数据加入到dataList
        if (zk != null) {
            watchNode(zk);
        }
    }

    /**
     * @return
     * zookpeeper发现服务字节点的数据
     */
    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);
                LOGGER.debug("using only data: {}", data);
            } else {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random data: {}", data);
            }
        }
        return data;
    }

    /**
     * @return
     * 链接zookeeper服务端
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                //
            	public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            try {
				latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				LOGGER.error("", e);
			}
        	} catch (IOException e) {
            LOGGER.error("", e);
        	}
        return zk;
    }

    /**
     * @param zk
     */
    private void watchNode(final ZooKeeper zk) {
        try {
        	//获取所有zookpeeper的registry子节点
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            //获取字节点下的数据加入dataList中
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            LOGGER.debug("node data: {}", dataList);
            this.dataList = dataList;
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }catch (KeeperException e) {
        	LOGGER.error("", e);
		}
    }
}