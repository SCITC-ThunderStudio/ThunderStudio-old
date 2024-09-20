package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import lombok.Getter;

/**
 * 响应体
 *
 * @author Nuyoahz
 * @date 2024/1/9
 */
@Getter
public class Response {
    /**
     * 状态码
     */
    private final Integer state;
    /**
     * 响应信息
     */
    private final String message;
    /**
     * 响应时间
     */
    private final String date;

    public Response(Integer state, String message) {
        this.state = state;
        this.message = message;
        this.date = CommonUtils.getTimeFormat(SystemConstant.YMDHMS);
    }

    public Response(StatusCodeConstant statusCodeConstant) {
        this.state = statusCodeConstant.getCode();
        this.message = statusCodeConstant.getMessage();
        this.date = CommonUtils.getTimeFormat(SystemConstant.YMDHMS);
    }

    public static Response info(Integer code, String message) {
        return new Response(code, message);
    }

    public static Response info(StatusCodeConstant systemConstant) {
        return new Response(systemConstant);
    }
}
