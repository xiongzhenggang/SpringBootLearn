package com.xzg.security.service.controller;

import netscape.security.Principal;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
//    @RequestMapping("/login")
//    public String loginGet() {
//        return "/html/login";
//    }
//    @PostMapping("login")
//    public String loginPost(Principal user) {
//
//        return "";
//    }
}