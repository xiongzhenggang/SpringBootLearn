package com.xzg.zuul.server.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xzg
 */
public class BCryptUtil {

    public static void main(String[] args){
        System.out.println(encodePassword("password"));
    }
    public static String encodePassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(password.trim());
        return encodedPassword;
    }
}
