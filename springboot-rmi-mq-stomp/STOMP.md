**springboot 使用STOMP**
### 主要配置均在项目com.xzg.cn.config下可见
1. STOMP主要配置
* 暂时使用代理为spring基于内存的。后续改为rabbitmq
```java
//配置stomp
@Configuration
@EnableWebSocketMessageBroker
public class StompConfig extends AbstractWebSocketMessageBrokerConfigurer {
// register your user interceptor by overriding configureClientInboundChannel
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/webSocketServer
        // 来和服务器的WebSocket连接
        stompEndpointRegistry.addEndpoint("/webSocketServer").setAllowedOrigins("*").withSockJS();
    }
    /**
     * 配置信息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称
        //enableSimpleBroker 是spring提供简单的代理，基于内存实现，如果需要更高级功能可以选择rabbit、activitie mq
        registry.enableSimpleBroker("/queue", "/topic");
        //启用stomp代理中继
        /*registry.enableStompBrokerRelay("/topic","/queue")
                .setRelayHost("rabbit.somewhere")
                .setRelayPort(62321)
                .setClientLogin("guest")
                .setClientPasscode("guest");*/
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         registry.setUserDestinationPrefix("/user/");
    }
}
```
2. STOMP需要了解部分WebSocket,这里不再赘述。他们关系：
```
1) HTTP协议解决了 web 浏览器发起请求以及 web 服务器响应请求的细节，假设 HTTP 协议 并不存在，只能使用 TCP 套接字来 编写 web 应用，你可能认为这是一件疯狂的事情；
2) 直接使用 WebSocket（SockJS） 就很类似于 使用 TCP 套接字来编写 web 应用，因为没有高层协议，就需要我们定义应用间所发送消息的语义，还需要确保连接的两端都能遵循这些语义；
3) 同 HTTP 在 TCP 套接字上添加请求-响应模型层一样，STOMP 在 WebSocket 之上提供了一个基于帧的线路格式层，用来定义消息语义
```
* 要说明的为stomp的几种名词：
```
STOMP帧:STOMP帧由命令，一个或多个头信息、一个空行及负载（文本或字节）所组成；
其中可用的COMMAND 包括：CONNECT、SEND、SUBSCRIBE、UNSUBSCRIBE、BEGIN、COMMIT、ABORT、ACK、NACK、DISCONNECT
```
* STOMP几种使用格式
```
发送消息:
    SEND
    destination:/queue/trade
    content-type:application/json
    content-length:44
    {“action”:”BUY”,”ticker”:”MMM”,”shares”,44}^@
订阅消息:
    SUBSCRIBE
    id:sub-1
    destination:/topic/price.stock.*
    ^@
服务器进行广播消息:
    MESSAGE
    message-id:nxahklf6-1
    subscription:sub-1
    destination:/topic/price.stock.MMM
    {“ticker”:”MMM”,”price”:129.45}^@

```
3. web页面使用stomp以及sockjs框架的api实现
```js
//引入相关框架
<script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>
<script type="text/javascript">
    // 建立连接对象（还未发起连接）
    var socket = new SockJS("http://localhost:8080/webSocketServer");
    // 获取 STOMP 子协议的客户端对象
    var stompClient = Stomp.over(socket);
        // 向服务器发起websocket连接并发送CONNECT帧
         stompClient.connect(
            {},//指定用户则输入相应header后续说明{name:'dd'},
            function connectCallback(frame) {
            // 连接成功时（服务器响应 CONNECTED 帧）的回调方法
            setMessageInnerHTML("连接成功");
            //客户端订阅地址
            stompClient.subscribe('/app/subscribeTest', function (response) {
                setMessageInnerHTML("已成功订阅/app/subscribeTest");
                var returnData = JSON.parse(response.body);
                setMessageInnerHTML("/app/subscribeTest 你接收到的消息为:" + returnData.responseMessage);
            });
        },
        function errorCallBack(error) {
            // 连接失败时（服务器响应 ERROR 帧）的回调方法
            setMessageInnerHTML("连接失败");
        }
    );
    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        var messageJson = JSON.stringify({ "name": message });
        stompClient.send("/app/sendTest", {}, messageJson);
        setMessageInnerHTML("/app/sendTest 你发送的消息:" + message);
    }
    //订阅消息
    function subscribe1() {
        stompClient.subscribe("/topic/subscribeTest", function (response) {
            setMessageInnerHTML("已成功订阅/topic/subscribeTest");
            var returnData = JSON.parse(response.body);
            setMessageInnerHTML("/topic/subscribeTest 你接收到的消息为:" + returnData.responseMessage);
        });
    }
    //订阅消息
    function subscribe2() {
        stompClient.subscribe("/topic/sendTest", function (response) {
            setMessageInnerHTML("已成功订阅/topic/sendTest");
            var returnData = JSON.parse(response.body);
            setMessageInnerHTML("/topic/sendTest 你接收到的消息为:" + returnData.responseMessage);
        });
    }
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }
    //以下为处理用户相关
    //用户订阅的信息,以user作为前缀的目的地将会一特殊的方式处理，服务端可根据用户名称发送指定的信息到web
     function subscribe3() {
        stompClient.subscribe("/user/queue/notifications", function (response) {
            setMessageInnerHTML("已成功订阅/user/queue/notifications");
            var returnData = JSON.parse(response.body);
            setMessageInnerHTML("/queue/notifications 你接收到的消息为:" + returnData.responseMessage);
        });
    }
</script>
```
4. 服务端使用spring的stomp。spring处理stomp协议可以和http协议一样简洁
* 首先看下普通sromp使用
```java
 @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //  @MessageMapping类似@RequestMapping
    @MessageMapping("/sendTest")
    //@SendTo 注解表示处理对应请求后要返回订阅的地址，若没有添加@SendTo注解返回的地址为（/topic/sendTest）
    @SendTo("/topic/subscribeTest")
    public ServerMessage sendDemo(ClientMessage message) {
        logger.info("接收到了信息：" + message.getName());
        return new ServerMessage("你发送的消息为:" + message.getName());
    }

    //与@MessageMapping不同的是@SubscribeMapping会直接将请求结果发送给客户端而不经过代理
    @SubscribeMapping("/subscribeTest")
    public ServerMessage sub() {
        logger.info("XXX用户订阅了我。。。");
        return new ServerMessage("感谢你订阅了我。。。");
    }

    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法（messagingTemplate可以不通过web，直接从服务端发送）
    @RequestMapping(value = "serverSendInfo",method = RequestMethod.GET)
    @ResponseBody
    public Spitter templateTest() {
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推到subscribeTest的数据"));
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/sendTest", new ServerMessage("服务器主动推到sendTest的数据"));
        return new Spitter(2,"dd");
    }
```
* 下面是关于发送有关以用户为基础的发送
```java
  @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
    @RequestMapping(value = "serveruUserSendInfo",method = RequestMethod.GET)
    @ResponseBody
    public Spitter templateTest() {
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推到subscribeTest的数据"));
        //服务端推送信息到客户端订阅/topic/subscribeTest的地址
        messagingTemplate.convertAndSend("/topic/sendTest", new ServerMessage("服务器主动推到sendTest的数据"));
        return new Spitter(2,"dd");
    }
    //测试对制定的用户发送推送
    //@MessageMapping和@SubscricbeMapping 标注的方法能够使用Principal来获取认证用户
    //@MessageMapping和@SubscricbeMapping @MessageExcepti标注的方法返回的值能够以消息的形式发送个认证用户
    //SimpMessageingTemplate能够发送给特定用户
    @MessageMapping("/spittle")
    @SendToUser("/queue/notifications")
    public ServerMessage handleSpittle(ClientMessage message){
        Spitter sp = new Spitter(4,message.getName());
        //一些其他操作
        System.out.println("发来的消息："+message.getName());
        return new ServerMessage("发给指定用户："+message.getName());
    }
    @RequestMapping(value = "sendAssageUser",method = RequestMethod.GET)
    @ResponseBody
    public void sendAssageUser(ClientMessage message) {
//        messagingTemplate.convertAndSend("/topic/spittlefeed","发给订阅topic/spittlefeed的");
        //下面的地址的目的地会变成/user/zhangsan/queue/notifications
        messagingTemplate.convertAndSendToUser(message.getName(),"/queue/notifications"
                ,new ServerMessage("主动发送给指定的用户"+message.getName()));
    }
```
5.  修改配置文件，启动rabbitmq作为代理使用
* 为方便测试使用，这里使用docker启动rabbit服务
```sh 
docker run -it --name rabbitmq -p 5671:5671 \
-p 5672:5672   -p 4369:4369  -p 15671:15671  -p 25672:25672 \
-p 15672:15672  -p 61613:61613 rabbitmq:management /bin/bash
# -it 和/bin/bash 是为了执行能进入后台执行下面的命令
# 1、加载rabbitmq的stomp插件: rabbitmq-plugins enable rabbitmq_stomp
# 2、重启rabbitmq服务，stomp插件生效后台运行： rabbitmq-server &
# 3、增加新用户和密码: rabbitmqctl  add_user  username password
# 4、为user增加角色否则无法登陆; rabbitmqctl  set_user_tags  username administrator
# 5、为该用户增加远程链接等权限：rabbitmqctl set_permissions -p / username ".*" ".*" ".*" 
```
* [rabbitmq 配置stomp插件官方](http://www.rabbitmq.com/stomp.html)

6. 将原来的配置文件configureMessageBroker方法修改为如下：
```java
 @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称
        //enableSimpleBroker 是spring提供简单的代理，基于内存实现，如果需要更高级功能可以选择rabbit、activitie mq
//        registry.enableSimpleBroker("/queue", "/topic");
        //启用stomp代理中继 启动Rabbit stomp插件： rabbitmq-plugins enable rabbitmq_stomp
        registry.enableStompBrokerRelay("/topic","/queue")
                .setRelayHost("192.168.1.101")
                //amqp:5672 clustering:5672  http:15672
                .setRelayPort(61613)
                .setClientLogin("xzg")
                .setClientPasscode("xzg")
                .setSystemLogin("xzg")
                .setSystemPasscode("xzg")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         registry.setUserDestinationPrefix("/user/");
    }
```
7. 重启后使用和简单代理使用相同即可。可访问http://ip:15672 网页访问查看