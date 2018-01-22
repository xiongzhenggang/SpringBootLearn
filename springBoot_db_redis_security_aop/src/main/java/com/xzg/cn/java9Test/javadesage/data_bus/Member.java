package com.xzg.cn.java9Test.javadesage.data_bus;

import java.util.function.Consumer;

public interface Member extends Consumer<DataType> {

    void accept(DataType event);
}