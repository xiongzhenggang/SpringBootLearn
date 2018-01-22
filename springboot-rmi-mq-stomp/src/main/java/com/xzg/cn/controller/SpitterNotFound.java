package com.xzg.cn.controller;

public class SpitterNotFound extends  RuntimeException {
    private long id;
    public SpitterNotFound(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
