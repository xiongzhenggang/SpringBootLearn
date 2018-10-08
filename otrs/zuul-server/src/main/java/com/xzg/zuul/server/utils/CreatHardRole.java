package com.xzg.zuul.server.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CreatHardRole {
    public static  Supplier<List<GrantedAuthority>> createAuthorities() {
        List<String> roleCodes = Arrays.asList("ROLE_USER","ADMIN", "USER");
        return () -> roleCodes.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
    }

    private <T> T convertToAuthorities(Supplier<T> predicate) {
//        List<GrantedAuthority> authorities = new ArrayList();
//        // 清除 Redis 中用户的角色
//        redisTemplate.delete(baseUser.getId());
//        roles.forEach(e -> {
//            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
//            GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
//            authorities.add(authority);
//            //存储角色到redis
//            redisTemplate.opsForList().rightPush(baseUser.getId(), e);
//        });
        return predicate.get();
    }
}
