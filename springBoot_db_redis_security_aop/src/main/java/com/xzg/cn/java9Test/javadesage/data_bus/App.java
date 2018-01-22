package com.xzg.cn.java9Test.javadesage.data_bus;

import com.xzg.cn.java9Test.javadesage.data_bus.data.MessageData;
import com.xzg.cn.java9Test.javadesage.data_bus.data.StartingData;
import com.xzg.cn.java9Test.javadesage.data_bus.data.StoppingData;
import com.xzg.cn.java9Test.javadesage.data_bus.members.MessageCollectorMember;
import com.xzg.cn.java9Test.javadesage.data_bus.members.StatusMember;

import java.time.LocalDateTime;

class App {

    public static void main(String[] args) {
        final DataBus bus = DataBus.getInstance();
        bus.subscribe(new StatusMember(1));
        bus.subscribe(new StatusMember(2));
        final MessageCollectorMember foo = new MessageCollectorMember("Foo");
        final MessageCollectorMember bar = new MessageCollectorMember("Bar");
        bus.subscribe(foo);
        bus.publish(StartingData.of(LocalDateTime.now()));
        bus.publish(MessageData.of("Only Foo should see this"));
        bus.subscribe(bar);
        bus.publish(MessageData.of("Foo and Bar should see this"));
        bus.unsubscribe(foo);
        bus.publish(MessageData.of("Only Bar should see this"));
        bus.publish(StoppingData.of(LocalDateTime.now()));
    }
}