package com.xzg.cn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlankDisc2 {
    private String name;
    private String info;
    @Autowired(required=true)
//    @Value("#{systemProperties['disc.title']}")
    public BlankDisc2(@Value("#{'mmp'}") String name,
                      @Value("#{'nxp'}") String info){
        this.name = name;
        this.info = info;
    }
    @Override
    public String toString() {
        return "BlankDisc2{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
