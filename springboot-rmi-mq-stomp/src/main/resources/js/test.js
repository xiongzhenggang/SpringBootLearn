function stompConnect() {
        var name = document.getElementById('username').value;
        // 向服务器发起websocket连接并发送CONNECT帧
         stompClient.connect(
            {name: name // Username!
            },
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
    )
  };
    //this line.
           function connect() {
               var userid = document.getElementById('userName').value;
               var socket = new SockJS("http://localhost:8080/webSocketServer");
               stompClient = Stomp.over(socket);
               stompClient.connect({}, function(frame) {
                   setConnected(true);
                   console.log('Connected: ' + frame);
                   stompClient.subscribe('/topic/greetings', function(greeting){
                       showGreeting(JSON.parse(greeting.body).content);
                   });
                   //订阅userid地址
                   stompClient.subscribe('/user/' + userid + '/message',function(greeting){
                       alert(JSON.parse(greeting.body).content);
                       showGreeting(JSON.parse(greeting.body).content);
                   });
               });
           }
