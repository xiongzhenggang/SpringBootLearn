package com.xzg.javaSimpleRpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**服务端有新的客户端连接进来的时候，就从客户端那里读取要调用的类名，
 * 方法名，然后通过反射找到并且调用这个方法，然后再把调用结果发送给客户端。*/
public class RpcPublisher {
	 private  static Logger logger = Logger.getLogger(RpcPublisher.class);
	@SuppressWarnings("resource")
	public static void main(String args[]) {
//		 PropertyConfigurator.configure( "../log4j.properties" );
		//网络对象序列化传递
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Socket clientSocket = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                clientSocket = ss.accept();//监听
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                //获取客户端写入的
                String serviceName = ois.readUTF();
                String methodName = ois.readUTF();
                logger.info("serviceName:"+serviceName+"=="+"methodName:"+methodName);
              //从客户端writeObject的对象,parameterTypes的参数类型
                Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
                Object[] parameters = (Object[]) ois.readObject();
                
                logger.info("parameterTypes.getClass():"+parameterTypes.getClass());
                logger.info("parameters.toString():"+parameters);
                Class<?> service = Class.forName(serviceName);
                logger.info("service.toString():"+service.toString());
                //获取客户端发送的代理对象，服务段执行相应的方法后，将数据发送会客户端
                Method method = service.getMethod(methodName, parameterTypes);
                //parameters:the arguments used for the method call
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
