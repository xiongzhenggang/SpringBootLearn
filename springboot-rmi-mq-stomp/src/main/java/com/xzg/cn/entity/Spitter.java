package com.xzg.cn.entity;

import java.io.Serializable;

public class Spitter implements Serializable {

    public Spitter(long id,String name){
        super();
        this.id = id;
        this.name = name;
    }
    public Spitter(String name){
        super();
        this.name = name;
    }
    public Spitter(){}
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Spitter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
