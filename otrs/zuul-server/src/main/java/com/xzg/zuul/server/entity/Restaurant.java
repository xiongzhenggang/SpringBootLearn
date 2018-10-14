package com.xzg.zuul.server.entity;


import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private List<Table> tables = new ArrayList<>();
    private String id;
    private boolean isModified;
    private String name;
    private String address;
    public Restaurant() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant(String name, String id, List<Table> tables) {
        this.tables = tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Table> getTables() {
        return tables;
    }
}
