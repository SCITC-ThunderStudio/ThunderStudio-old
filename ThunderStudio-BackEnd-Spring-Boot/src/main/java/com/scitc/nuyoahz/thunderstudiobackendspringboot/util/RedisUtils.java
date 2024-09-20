package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Nuyoahz
 * @date 2024/1/11 15:44
 */
@Component
@Log4j2
public class RedisUtils {
    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置Key
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     * @author Nuyoahz
     * @date 2024/01/11 15:44
     */
    public static void setKey(String key, Object value, Long time) {
        redisTemplate.opsForValue().set(key, value);
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            log.error("验证码写入Redis失败");
            ApiException.setError(StatusCodeConstant.SYSTEM_ERROR);
        }
        if (!setExpire(key, time)) {
            log.error("验证码过期时间设置失败");
            ApiException.setError(StatusCodeConstant.SYSTEM_ERROR);
        }
    }

    /**
     * 设置过期时间
     *
     * @param key  键
     * @param time 过期时间
     * @return java.lang.Boolean
     * @author Nuyoahz
     * @date 2024/01/11 15:45
     */
    public static Boolean setExpire(String key, Long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return java.lang.Object
     * @author Nuyoahz
     * @date 2024/01/11 15:45
     */
    public static Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除Key
     *
     * @param key 键
     * @return java.lang.Boolean
     * @author Nuyoahz
     * @date 2024/01/11 15:46
     */
    public static Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 键对应的值是否存在
     *
     * @param key 键
     * @return java.lang.Boolean
     * @author Nuyoahz
     * @date 2024/01/11 15:46
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }
}
