package com.scitc.nuyoahz.thunderstudiobackendspringboot.exception;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import lombok.Getter;

/**
 * 自定义异常类
 *
 * @author Nuyoahz
 * @date 2024/1/11 13:47
 */
@Getter
public class ApiException extends RuntimeException {
    /**
     * 错误代码
     */
    private final Integer errorCode;
    /**
     * 错误信息
     */
    private final String errorMessage;

    private ApiException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    private ApiException(StatusCodeConstant statusCodeConstants) {
        super(statusCodeConstants.getMessage());
        errorCode = statusCodeConstants.getCode();
        errorMessage = statusCodeConstants.getMessage();
    }

    public static void setError(Integer errorCode, String message) {
        throw new ApiException(errorCode, message);
    }

    public static void setError(StatusCodeConstant statusCodeConstants) {
        throw new ApiException(statusCodeConstants);
    }
}