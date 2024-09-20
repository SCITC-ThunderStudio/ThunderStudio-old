package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import jakarta.servlet.http.Cookie;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie工具
 *
 * @author Nuyoahz
 * @date 2024/1/11 13:41
 */
@Log4j2
public class CookieUtils {
    /**
     * 设置Cookie
     *
     * @param key    健
     * @param value 值
     * @param maxAge Cookie有效时间
     * @return jakarta.servlet.http.Cookie
     * @author Nuyoahz
     * @date 2024/01/11 13:42
     */
    public static Cookie setCookie(String key, String value, Integer maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        // cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 删除Cookie
     *
     * @return jakarta.servlet.http.Cookie
     * @author Nuyoahz
     * @date 2024/01/11 13:44
     */
    public static Cookie deleteCookies() {
        Cookie cookie = new Cookie("userInfo", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 解析Cookie数组
     *
     * @param cookies Cookie数组
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Nuyoahz
     * @date 2024/01/11 13:45
     */
    public static Map<String, Object> analyzeCookie(Cookie[] cookies) {
        String value = "";

        if (cookies == null) {
            log.error("Cookie为空");
            ApiException.setError(StatusCodeConstant.COOKIE_ERROR);
        }

        for (Cookie c : cookies) {
            if ("userInfo".equals(c.getName())) {
                value = c.getValue();
                break;
            }
        }

        if (value.isEmpty()) {
            log.error("用户信息为空");
            ApiException.setError(StatusCodeConstant.COOKIE_ERROR);
        }

        return analyzeCookie(value);
    }


    /**
     * 解析Cookie值
     *
     * @param value Cookie值
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Nuyoahz
     * @date 2024/01/11 13:50
     */
    public static Map<String, Object> analyzeCookie(String value) {
        String[] str = value.split(":", 6);
        return new HashMap<>(5) {
            {
                put("studentId", Integer.valueOf(str[0]));
                put("studentClass", str[1]);
                put("name", str[2]);
                put("authority", Integer.valueOf(str[5]));
            }
        };
    }
}
