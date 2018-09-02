package com.xzg.common;

/**
 * @author xzg
 */

public enum ResultCode {
    //成功
    SUCCESS(200, "成功"),
    //请求出现错误，比如请求头不对等，所有不想明确区分的客户端请求出错都可以返回 400
    REQUEST_FAILUR(400,"请求出现错误"),
   //没有提供认证信息。比如说，请求的时候没有带上 Token 等。
    PARAM_INVALID(401, "参数无效"),
    //403资源不允许访问
    NO_ACCESS(403, "资源不允许访问"),
    TASK_NULL(404, "任务不存在"),
    //500服务器错误。没法明确定义的服务器错误都可以返回这个
    SERVER_EXCEPTION(500,"服务器异常"),
    //1024 这里就是单纯错误信息
    BASE_FAILURE(1024,"失败");
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
