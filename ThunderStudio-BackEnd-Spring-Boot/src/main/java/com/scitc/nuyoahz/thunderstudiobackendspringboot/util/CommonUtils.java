package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.ThunderStudioBackEndSpringBootApplication;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用工具
 *
 * @author Nuyoahz
 * @date 2024/1/9
 */
@Log4j2
public class CommonUtils {
    /**
     * 获取指定时间格式
     *
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/09
     */
    public static String getTimeFormat(String format) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        // 设置时区
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 格式化时间
        return simpleDateFormat.format(date);
    }

    /**
     * 获取指定时间格式
     *
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/09
     */
    public static String getTimeFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        // 格式化时间
        return simpleDateFormat.format(date);
    }

    /**
     * 获取jar包绝对路径
     *
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/14
     */
    public static String getJarAbsolutePath() {
        String absolutePath = ThunderStudioBackEndSpringBootApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return absolutePath.substring(0, absolutePath.indexOf("ThunderStudio-BackEnd")).replaceAll("nested:", "");
    }

    /**
     * 获取redis用户信息
     *
     * @return java.util.List<java.lang.Integer>
     * @author Nuyoahz
     * @date 2024/03/18
     */
    public static List<Integer> getRedisUserInfo(HttpServletRequest request) {
        String sessionId = request.getHeader(SystemConstant.HEART_SESSION);
        String userInfo = (String) RedisUtils.getKey(sessionId);
        String[] str = userInfo.split(":", 2);
        return new ArrayList<>(2) {
            {
                add(Integer.parseInt(str[0]));
                add(Integer.parseInt(str[1]));
            }
        };
    }

    /**
     * 获取AES密钥和偏移量
     *
     * @param request 请求
     * @return java.util.List<java.lang.String>
     * @author Nuyoahz
     * @date 2024/04/15
     */
    public static List<String> getAESKey(HttpServletRequest request) {
        String key = RSAUtiles.decrypt(request.getHeader(SystemConstant.HEART_KEY));
        String iv = RSAUtiles.decrypt(request.getHeader(SystemConstant.HEART_IV));
        return Arrays.asList(key, iv);
    }
}