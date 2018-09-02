package com.xzg.common.BusinessException;

import com.xzg.common.ResultCode;

/**
 * @author xzg
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 2984474148327146104L;
    private ResultCode resultCode;

    /**
     * @param resultCode
     */
    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}

