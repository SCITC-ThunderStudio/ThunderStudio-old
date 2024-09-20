package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 多线程任务工具
 *
 * @author Nuyoahz
 * @date 2024/05/10
 */
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
@Component
@Log4j2
public class MultiThreadTaskUtils {
    private final UserServiceImpl userService;

    /**
     * 将今日未打卡人员保存进Redis中,每天23点56分执行
     *
     * @author Nuyoahz
     * @date 2024/5/10
     */
    @Async
    @Scheduled(cron = "0 56 23 * * ?")
    public void saveClockInListF() {
        log.info("开始保存未打卡人员 Time:{} Thread:{}", LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
        // 获取今日未打卡人员
        List<ClockInListF> clockInListF = userService.getClockInListRedis();
        // 转化为JSON字符串
        String jsonStr = JSONUtil.toJsonStr(clockInListF);
        // 保存到Redis中,过期时间为1天
        RedisUtils.setKey("ClockInListF", jsonStr, 86400L);
        // 查看值是否设置成功
        if (RedisUtils.hasKey("ClockInListF")) {
            log.info("未打卡人员保存成功");
        } else {
            log.error("未打卡人员保存失败");
        }
    }

    /**
     * 将本周未提交周报人员保存进Redis中,星期天23点57分执行
     *
     * @author Nuyoahz
     * @date 2024/5/11
     */
    @Async
    @Scheduled(cron = "0 57 23 ? * SUN")
    public void savaWeeklyListF() {
        log.info("开始保存未提交周报人员 Time:{} Thread:{}", LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
        List<WeeklyListF> weeklyListRedis = userService.getWeeklyListRedis();
        String jsonStr = JSONUtil.toJsonStr(weeklyListRedis);
        RedisUtils.setKey("WeeklyListF", jsonStr, 604800L);
        if (RedisUtils.hasKey("WeeklyListF")) {
            log.info("未提交周报人员保存成功");
        } else {
            log.error("未提交周报人员保存失败");
        }
    }

    /**
     * 重置人员打卡状态,每天23点58分执行
     *
     * @author Nuyoahz
     * @date 2024/5/10
     */
    @Async
    @Scheduled(cron = "0 58 23 * * ?")
    public void resetClockInState() {
        log.info("开始重制人员打卡状态 Time:{} Thread:{}", LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
        userService.resetClockInState();
    }

    /**
     * 重置人员周报状态,星期天23点59分执行
     *
     * @author Nuyoahz
     * @date 2024/5/10
     */
    @Async
    @Scheduled(cron = "0 59 23 ? * SUN")
    public void resetWeeklyState() {
        log.info("开始重制人员周报状态 Time:{} Thread:{}", LocalDateTime.now().toLocalTime(), Thread.currentThread().getName());
        userService.resetWeeklyState();
    }
}