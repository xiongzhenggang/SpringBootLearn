package com.xzg.security.service.securityEtity;

public class BaseUser {
    String userName;
    String  password;

    public  BaseUser(){}
    public BaseUser(String name,String pass){
        this.userName = name;
        this.password = pass;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
