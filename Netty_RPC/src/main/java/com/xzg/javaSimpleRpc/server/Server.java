package com.xzg.javaSimpleRpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
/**
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * @author xzg
 * 2018年2月8日
 * @ClassName: RpcPublisher.java
 * @Description: 服务端有新的客户端连接进来的时候，就从客户端那里读取要调用的类名，方法名，
 * 然后通过反射找到并且调用这个方法，然后再把调用结果发送给客户端。 
 * @version: v1.0.0
 */
public class Server {
	 private  static Logger logger = Logger.getLogger(Server.class);
	 public static int PORT  = 8081;
	@SuppressWarnings("resource")
	public static void main(String args[]) {
		//网络对象序列化传递
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Socket clientSocket = null;
        ServerSocket ss = null;
        try {
        //开启服务端socket
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
            	//阻塞监听
                clientSocket = ss.accept();
                //将监听到的socket流包装成对应的对象输入输出流
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                //获取客户端写入的,读取客户端要调用的类和方法名，一下的读取对应客户端的发送
                String serviceName = ois.readUTF();
                String methodName = ois.readUTF();
                logger.info("客户端调用的类为:"+serviceName+" 方法名为:"+methodName);
                //从客户端writeObject的对象,parameterTypes的参数类型
                Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
                Object[] parameters = (Object[]) ois.readObject();
                logger.info("客户端调用的参数类型: "+parameterTypes.getClass().getTypeName()+" 客户端发送参数: "+parameters.toString());
                //Class.forName 获取jvm中名称为serviceName的类
                Class<?> service = Class.forName(serviceName);
                logger.info("取得服务端jvm中的类 :"+service.toString());
                //获取客户端发送的代理对象，服务端执行相应的方法后，将数据发送会客户端
                Method method = service.getMethod(methodName, parameterTypes);
                //服务端执行相应的方法后，返回值给客户端，写入socket流
                oos.writeObject(method.invoke(service.newInstance(), parameters));
            } catch (Exception e) {
            	e.printStackTrace();
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
