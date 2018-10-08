package com.xzg.api.service.restaurant.entityDto;

import java.math.BigInteger;

/**
 * @author xzg
 */
public class Table {

    private int capacity;

    public Table(String name, BigInteger id, int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}