package com.xzg.cn.common;

public class BlankDisc {
    private String name;
    private String info;
    public BlankDisc(String name, String info){
        this.name = name;
        this.info = info;
    }
    @Override
    public String toString() {
        return "BlankDisc{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
