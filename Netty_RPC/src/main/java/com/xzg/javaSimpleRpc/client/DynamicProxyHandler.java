package com.xzg.javaSimpleRpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * @author xzg
 * 2018年2月8日
 * @ClassName: DynamicProxyHandler.java
 * @Description: 用于调用代理接口
 * @version: v1.0.0
 */
public class DynamicProxyHandler implements InvocationHandler {
	public static int PORT = 8081;
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket s = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            s = new Socket();
            s.connect(new InetSocketAddress("localhost", PORT));
            //连接后取得socket输入输出流包装成对象输入输出流（原因是包装后成对象流方便使用）
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            //发送字符串，这里是告诉服务端客户端要调用那个类
            oos.writeUTF("com.xzg.javaSimpleRpc.server.EchoServiceImple");
            //在告诉调用这个类的哪个方法名称
            oos.writeUTF(method.getName());
            //告诉服务端调用方法的参数类型
            oos.writeObject(method.getParameterTypes());
            //发送客户端调用方法的参数
            oos.writeObject(args);
            System.out.println("客户端调用方法名称："+method.getName()+"\n"
            					+"参数类型："+method.getParameterTypes()+"\n"
            					+"参数："+printArray(args));
            //读取服务端发送的数据，这一过程为阻塞
            Object object = ois.readObject();
            //返回客户端发送的对象，然后代理到客户端
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null)
                s.close();
            if (ois != null)
                ois.close();
            if (oos != null)
                oos.close();
        }
        return null;
    }
	//读取数组
	private String printArray(Object[] args){
		StringBuilder sb = new StringBuilder();
		for(Object ob: args){
			sb.append(ob.toString()+",");
		}
		return sb.toString();
	}
}
