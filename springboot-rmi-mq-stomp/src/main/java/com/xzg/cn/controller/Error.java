package com.xzg.cn.controller;

public class Error {
    private long code;
    private String Message;
    public Error(){}
    public Error(long code,String Message){
        this.code = code;
        this.Message = Message;
    }
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
