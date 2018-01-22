package com.xzg.cn.java9Test.javadesage.data_bus;

public interface DataType {

    /**
     * Returns the data-bus the event is being sent on.
     *
     * @return The data-bus
     */
    DataBus getDataBus();

    /**
     * Set the data-bus the event will be sent on.
     *
     * @param dataBus The data-bus
     */
    void setDataBus(DataBus dataBus);
}