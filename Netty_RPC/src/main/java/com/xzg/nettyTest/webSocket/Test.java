package com.xzg.nettyTest.webSocket;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;

public class Test {

/**	使用 ScheduledExecutorService 工作的很好，但是有局限性，比如在一个额外的线程中执行任务。
	如果需要执行很多任务，资源使用就会很严重；
	对于像 Netty 这样的高性能的网络框架来说，严重的资源使用是不能接受的。Netty 对这个问题提供了很好的方法。*/
	public static void main(String[] args){
		ScheduledExecutorService executor = Executors
		        .newScheduledThreadPool(10); //1新建 ScheduledExecutorService 使用10个线程
		ScheduledFuture<?> future = executor.schedule(
		        new Runnable() { //2新建 runnable 调度执行
		            public void run() {
		                System.out.println("executor");  //3稍后运行
		            }
		        }, 2, TimeUnit.SECONDS);  //4
		// do something
		executor.shutdown();  //5关闭 ScheduledExecutorService 来释放任务完成的资源
		nettyTest();
	}
	private static void nettyTest(){
		Channel ch = null; // Get reference to channel
		ScheduledFuture<?> future = ch.eventLoop().schedule(
		        new Runnable() {
		            public void run() {
		            	System.out.println("Now its 5 seconds later");
		            }
		        }, 5,TimeUnit.SECONDS);
		try {
			Object object = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
