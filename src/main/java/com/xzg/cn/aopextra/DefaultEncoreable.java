package com.xzg.cn.aopextra;

public class DefaultEncoreable implements  Encoreable {
    @Override
    public void performEncore() {
            System.out.println("默认接口引入了新的功能！");
    }
}
