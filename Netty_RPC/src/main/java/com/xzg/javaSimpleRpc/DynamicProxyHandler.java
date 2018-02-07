package com.xzg.javaSimpleRpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DynamicProxyHandler implements InvocationHandler {
	//this class is used to invoke proxyed instance
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket s = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            s = new Socket();
            s.connect(new InetSocketAddress("localhost", 8081));
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            //发送字符串
            oos.writeUTF("com.xzg.rpc.EchoServiceImple");//send first 
            oos.writeUTF(method.getName());//send second
            //发送对象类型的数据
            oos.writeObject(method.getParameterTypes());//发送客户端调用方法一些列的参数
            oos.writeObject(args);
            System.out.println("我猜这个为："+args[0].toString());
            //阻塞读取服务端发送的数据
           Object object = ois.readObject();//返回服务端执行结果
            return object;//read service,block 
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
}
