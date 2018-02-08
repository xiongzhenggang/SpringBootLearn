## 主要对netty的学习，最终实现netty + zookeeper 实现一个基本的RPC
** 1. 使用java中的socket 实现最初基本的RPC . com.xzg.javaSimpleRpc包下**
1) 定义客户端和服务端调用的接口（因为是本地这里服务端省略EchoService接口，直接使用客户端的接口）
```java
public interface EchoService {
	String echo(String request);
	String read(String request);
}
```
2）客户端不需要实现该接口，服务端实现即可
```java
public class EchoServiceImple implements EchoService{

	public String echo(String request) {
		// TODO Auto-generated method stub
		return "echo:"+request;
	}

	public String read(String request) {
		// TODO Auto-generated method stub
		return "服务端发送添加 "+request;
	}
}
```
3) 使用jdk动态代理，DynamicProxyHandler通过socket请求获取服务端运行的数据

```java
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
```
4) 客户端调用功
```java
/**把socket通信都隐藏起来，让客户端只知道调用echo接口*/
public class Caller {
	public static void main(String args[]) {
		//jdk的动态代理是基于接口的，所以这里的接口与服务端相同
        EchoService echo = (EchoService)Proxy.newProxyInstance(
        		//使用该接口的类加载器，为DynamicProxyHandler
        		EchoService.class.getClassLoader(),
                new Class<?>[]{EchoService.class},
              //DynamicProxyHandler为获取服务端返回的对象包装成代理
                new DynamicProxyHandler());
        //客户端使用，连续
        for (int i = 0; i < 3; i++) {
            System.out.println(echo.echo(String.valueOf(i)));
        }
    }
}
```
5) 服务端也是通过socket运行服务端有新的客户端连接进来的时候，就从客户端那里读取要调用的类名，方法名，然后通过反射找到并且调用这个方法，然后再把调用结果发送给客
```java
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
```

** 2. 包含一些Java的设计模式可参考[java部分设计模式](https://github.com/xiongzhenggang/xiongzhenggang.github.io/tree/master/java23%E7%A7%8D%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F) **

** 3. netty 的学习了解 **

** 4. 搭建zookeeper 使用netty 实现功能更强大的RPC **