package com.scitc.nuyoahz.thunderstudiobackendspringboot;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.DESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class ThunderStudioBackEndSpringBootApplicationTests {

    @Test
    void RandomTest() {
        String string = RandomUtil.randomString(6);
        System.out.println(string);
    }

    @Test
    void DESTest() {
        String string = "Scitc123456";
        System.out.println(DESUtils.encrypt(string));
    }

    @Test
    void PortTest() {
        System.out.println(Objects.equals(21201139, SystemConstant.ADMIN_UID));
    }

    @Test
    void ClockInListFRedisTest() {
        ClockInListF clockInListF = new ClockInListF();
        clockInListF.setName("张三");
        clockInListF.setUid(20301023);
        clockInListF.setStudentClass("软件20-1");
        ClockInListF clockInListFs = new ClockInListF();
        clockInListFs.setName("张三");
        clockInListFs.setUid(20301023);
        clockInListFs.setStudentClass("软件20-1");
        ArrayList<Object> objects = new ArrayList<>() {
            {
                add(clockInListF);
                add(clockInListFs);
            }
        };
        String jsonStr = JSONUtil.toJsonStr(objects);
        System.out.println(jsonStr);
        RedisUtils.setKey("ClockInListF", jsonStr, 86400L);
        String clockInListF1 = (String) RedisUtils.getKey("ClockInListF");
        List<ClockInListF> list = JSONUtil.toList(clockInListF1, ClockInListF.class);
        System.out.println(list.get(0).getName());
    }

    @Test
    void weeklyListFRedisTest() {
        WeeklyListF weeklyListF = new WeeklyListF();
        weeklyListF.setUid(20301023);
        weeklyListF.setStudentClass("软件20-1");
        weeklyListF.setName("沈艺辉");
        WeeklyListF weeklyListFs = new WeeklyListF();
        weeklyListFs.setUid(20301023);
        weeklyListFs.setStudentClass("物联网20-1");
        weeklyListFs.setName("刘瑞超");
        ArrayList<Object> objects = new ArrayList<>() {
            {
                add(weeklyListF);
                add(weeklyListFs);
            }
        };
        String jsonStr = JSONUtil.toJsonStr(objects);
        System.out.println(jsonStr);
        RedisUtils.setKey("weeklyListF", jsonStr, 604800L);
        String weeklyListF1 = (String) RedisUtils.getKey("weeklyListF");
        List<WeeklyListF> list = JSONUtil.toList(weeklyListF1, WeeklyListF.class);
        System.out.println(list.get(0).getName());
    }

    @Test
    void test() {
        RedisUtils.deleteKey("KBvLFh25ZMaPMtoc");
    }
}
