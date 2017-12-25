package com.xzg.cn.java9Test.javadesage.data_bus.data;

import com.xzg.cn.java9Test.javadesage.data_bus.AbstractDataType;
import com.xzg.cn.java9Test.javadesage.data_bus.DataType;
public class MessageData extends AbstractDataType {

    private final String message;

    public MessageData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static DataType of(final String message) {
        return new MessageData(message);
    }
}