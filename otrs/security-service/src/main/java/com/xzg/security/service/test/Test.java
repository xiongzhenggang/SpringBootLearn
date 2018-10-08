package com.xzg.security.service.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    public static void  main(String[] args){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		// 加密
         String encodedPassword = passwordEncoder.encode("password");
         System.out.println(encodedPassword);
    }
}
