package com.xzg.cn.common;

import java.util.List;

public class CompactDisc {
    private String name;
    private String info;
    private List<String> trancks;
    public CompactDisc(String name, String info){
        this.name = name;
        this.info = info;
    }
    public CompactDisc(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getTrancks() {
        return trancks;
    }

    public void setTrancks(List<String> trancks) {
        this.trancks = trancks;
    }

    public void playTranks(int trank){
        if(trank > trancks.size())
            System.out.println("播放超出磁道长度！");
        System.out.println("播放第："+trank+"磁道"+"\n"
        +trancks.get(trank));
    }
    @Override
    public String toString() {
        return "BlankDisc{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
