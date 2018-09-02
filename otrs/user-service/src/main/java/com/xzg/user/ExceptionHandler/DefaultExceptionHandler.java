package com.xzg.user.ExceptionHandler;

import com.xzg.common.BusinessException.CustomException;
import com.xzg.common.ResultCode;
import com.xzg.common.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xzg
 * 拦截controller转发的请求过程中产生的异常
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 处理所有自定义异常 * @param e * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResultJson handleCustomException(CustomException e) {
        return ResultJson.failure(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)

    public ResultJson handleException(Exception e) {
        logger.error(e.getMessage());
        return ResultJson.failure(ResultCode.SERVER_EXCEPTION);
    }
}

