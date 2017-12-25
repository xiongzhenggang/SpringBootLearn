package com.xzg.cn.java9Test.javadesage.data_bus.members;

import com.xzg.cn.java9Test.javadesage.data_bus.DataType;
import com.xzg.cn.java9Test.javadesage.data_bus.data.MessageData;
import com.xzg.cn.java9Test.javadesage.data_bus.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
public class MessageCollectorMember implements Member {

    private static final Logger LOGGER = Logger.getLogger(MessageCollectorMember.class.getName());

    private final String name;

    private List<String> messages = new ArrayList<>();

    public MessageCollectorMember(String name) {
        this.name = name;
    }

    @Override
    public void accept(final DataType data) {
        if (data instanceof MessageData) {
            handleEvent((MessageData) data);
        }
    }

    private void handleEvent(MessageData data) {
        LOGGER.info(String.format("%s sees message %s", name, data.getMessage()));
        messages.add(data.getMessage());
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}