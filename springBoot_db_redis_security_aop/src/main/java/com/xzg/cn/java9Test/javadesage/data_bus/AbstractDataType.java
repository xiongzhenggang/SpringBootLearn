package com.xzg.cn.java9Test.javadesage.data_bus;

public class AbstractDataType implements DataType {

    private DataBus dataBus;

    @Override
    public DataBus getDataBus() {
        return dataBus;
    }

    @Override
    public void setDataBus(DataBus dataBus) {
        this.dataBus = dataBus;
    }
}