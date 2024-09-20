package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import lombok.Getter;

/**
 * 响应体（带数据）
 *
 * @author Nuyoahz
 * @date 2024/1/9
 */
@Getter
public class ResponseData<T> extends Response {
    private final T data;

    private ResponseData(Integer code, String massage, T data) {
        super(code, massage);
        this.data = data;
    }

    private ResponseData(StatusCodeConstant systemConstant, T data) {
        super(systemConstant);
        this.data = data;
    }

    public static <T> ResponseData<T> info(T data) {
        return new ResponseData<>(StatusCodeConstant.SUCCESS, data);
    }

    public static <T> ResponseData<T> info(StatusCodeConstant systemConstant, T data) {
        return new ResponseData<>(systemConstant, data);
    }

    public static <T> ResponseData<T> info(Integer code, String massage, T data) {
        return new ResponseData<>(code, massage, data);
    }
}