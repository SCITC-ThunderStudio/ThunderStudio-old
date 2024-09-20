package com.scitc.nuyoahz.thunderstudiobackendspringboot.config;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕获并处理异常
 *
 * @author Nuyoahz
 * @date 2024/1/11 15:48
 */
@RestControllerAdvice
public class GlobalExceptionConfig {
    /**
     * 处理自定义异常类
     *
     * @param apiException 异常
     * @return Response
     * @author Nuyoahz
     * @date 2024/01/11 15:49
     */
    @ExceptionHandler(ApiException.class)
    public Response apiExceptionHandler(ApiException apiException) {
        return Response.info(apiException.getErrorCode(), apiException.getMessage());
    }

    /**
     * 处理全局异常类
     *
     * @param runtimeException 异常
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/01/11 15:49
     */
    @ExceptionHandler(RuntimeException.class)
    public Response apiRuntimeException(RuntimeException runtimeException) {
        return Response.info(500, "内部服务器错误，请联系管理员: " + runtimeException.getMessage());
    }
}
