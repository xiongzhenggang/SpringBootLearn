## 主要对netty的学习，最终实现netty + zookeeper 实现一个基本的RPC
* 1. 使用java中的socket 实现最初基本的RPC . com.xzg.javaSimpleRpc包下
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

* 2. 包含一些Java的设计模式可参考[java部分设计模式](https://github.com/xiongzhenggang/xiongzhenggang.github.io/tree/master/java23%E7%A7%8D%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F) **

* 3. netty 的学习了解 

* 4. 搭建zookeeper 使用netty 实现功能更强大的RPC 。整个项目在com.xzg.nettyRpc
* 使用doker启动zk
```sh
docker run -p 2181:2181 -p 2888:2888 -p 3888:3888 -d jplock/zookeeper
```

* 项目核心解释：
```
1. 启动服务端
通过new ClassPathXmlApplicationContext("server-spring.xml"); 会直接加载服务端组件，并在初始化
时，启动netty服务端（在pipeline中加入编码解码功能以及处理客户端调用的接口实现功能）。并且将服务端的ip port添加到zookeeper上。
2. 启动客户端
调用客户端代理rpcProxy执行，先从zk中取得服务端的ip port后 -> 调用客户端的netty服务发送调用的接口信息 -> 阻塞等待服务端返回的结果
```
* 代码部分
RpcBootstrap 启动服务端
```java
public class RpcBootstrap {

	 @SuppressWarnings("resource")
	public static void main(String[] args) {
		//首先加载启动服务端。监听客户端的到来
	        new ClassPathXmlApplicationContext("server-spring.xml");
}
}
```
RpcServer服务收集自定义注解接口
```java
 /** 
     * 实现setApplicationContext接口
     * rpcServer初始化的时候调用，目的是将所有添加了注解RpcService的注解类找到
     * 获取其接口名称，作为key，添加该注解的类作为value封装到handlemap中。
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    	// 获取所有带有 RpcService 注解的 Spring Bean
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
            	//获取使用了@RpcService的所有接口，放入handlerMap
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                LOGGER.info("收集使用了@RpcService的类："+interfaceName);
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }
/**
     * 在bean初始化之前会调用afterPropertiesSet()
     * 通过初始化时，启动netty—rpcserver。并且在通道中添加相应的处理器。
     * 其中RpcHandler处理器的作用为
     */
 public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//启动服务端
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                        	.addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,0))// 处理tcp粘包问题
                            .addLast(new RpcDecoder(RpcRequest.class)) // 服务端，将 RPC 请求进行解码（为了处理请求）
                            .addLast(new RpcEncoder(RpcResponse.class)) // 服务端，将 RPC 响应进行编码（为了返回响应）
                            .addLast(new RpcHandler(handlerMap)); // 做后处理 RPC 请求
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            LOGGER.debug("server started on port {}", port);
            if (serviceRegistry != null) {
                serviceRegistry.register(serverAddress); // 注册服务地址
            }

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }	
```
客户端代理类RpcProxy：取得zk中服务端的地址后，调用客户端执行
```java
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            /**
             * @author xzg
             *	newProxyInstance第三个参数，重写invoke方法
             */
            new InvocationHandler() {
            	//invoke方法主要是和服务端通信获取服务端发送过来的代理对象
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                	//将客户端发送的请求封装对象，序列化传输
                    RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);
                    //zookeeper中的发现服务目的是通过zookeeper作为服务端的管理者，当多个服务做负载均衡可以不用人工去干预
                    if (serviceDiscovery != null) {
                        serverAddress = serviceDiscovery.discover(); // 发现服务
                    }

                    String[] array = serverAddress.split(":");
                    String host = array[0];
                    int port = Integer.parseInt(array[1]);

                    RpcClient client = new RpcClient(host, port); // 初始化 RPC 客户端
                    RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应

                    if (response.isError()) {
                        throw response.getError();
                    } else {
                        return response.getResult();
                    }
                }
            }
        );
    }
```
客户端执行程序RpcClient
```java
  public RpcResponse send(RpcRequest request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
            .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                            .addLast(new RpcEncoder(RpcRequest.class)) // 将 RPC 请求进行编码（为了发送请求）
                            .addLast(new RpcDecoder(RpcResponse.class)) // 将 RPC 响应进行解码（为了处理响应）
                            .addLast(RpcClient.this); // 使用 RpcClient 发送 RPC 请求
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().writeAndFlush(request).sync();

            synchronized (obj) {
                obj.wait(); // 未收到响应，使线程等待
            }

            if (response != null) {
                future.channel().closeFuture().sync();
            }
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }
```
* 具体可参考项目源码