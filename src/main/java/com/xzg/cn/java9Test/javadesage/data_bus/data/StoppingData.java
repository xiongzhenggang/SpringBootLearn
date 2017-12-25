package com.xzg.cn.java9Test.javadesage.data_bus.data;

import com.xzg.cn.java9Test.javadesage.data_bus.AbstractDataType;
import com.xzg.cn.java9Test.javadesage.data_bus.DataType;
import java.time.LocalDateTime;

public class StoppingData extends AbstractDataType {

    private final LocalDateTime when;

    public StoppingData(LocalDateTime when) {
        this.when = when;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public static DataType of(final LocalDateTime when) {
        return new StoppingData(when);
    }
}