package com.xzg.cn.entity;

import java.security.Principal;

//为使用stomp推送信息给指定的用户
public final class User implements Principal {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
