package com.xzg.common;


import java.io.Serializable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author xzg
 */
public class ResultJson<T> implements Serializable {
    private static final long serialVersionUID = 783015033603078674L;
    /**
     * 基础成功对象，不需要多次创建
     */
    private final static ResultJson SUCCESS_RESULT = new ResultJson(ResultCode.SUCCESS);
    private int code;
    private String msg;
    private T data;

    public ResultJson(ResultCode resultCode) {
        setResultCode(resultCode);
        this.data = data;
    }

    public ResultJson(ResultCode resultCode, T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public ResultJson(ResultCode resultCode, T data, String msg) {
        this.code = resultCode.getCode();
        this.msg = msg;
        this.data = data;
    }

    public final static <T> ResultJson baseSuccess() {
        return SUCCESS_RESULT;
    }

    public final static <T> ResultJson success(Optional<T> optional) {
        return new ResultJson(ResultCode.SUCCESS, optional.get());
    }

    public final static <T> ResultJson success(T data) {
        return new ResultJson(ResultCode.SUCCESS, data);
    }

    public final static <T> ResultJson baseFailure(Supplier<T> supplier) {
        ResultJson resultJson = new ResultJson(ResultCode.BASE_FAILURE, supplier.get());
        return resultJson;
    }

    public final static <T> ResultJson failure(ResultCode resultCode, Supplier<T> supplier ,String msg) {
        ResultJson resultJson = new ResultJson(ResultCode.BASE_FAILURE, supplier.get(),msg);
        return resultJson;
    }
    public final static <T> ResultJson failure(ResultCode resultCode) {
        ResultJson resultJson = new ResultJson(ResultCode.BASE_FAILURE);
        return resultJson;
    }


    public final void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public final int getCode() {
        return code;
    }

    public final void setCode(int code) {
        this.code = code;
    }

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(String msg) {
        this.msg = msg;
    }

    public final T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

