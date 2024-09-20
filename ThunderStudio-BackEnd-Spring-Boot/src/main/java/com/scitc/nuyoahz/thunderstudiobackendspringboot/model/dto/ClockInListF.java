package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 今日未打卡学生集合
 *
 * @author Nuyoahz
 * @date 2024/1/14
 */
@Getter
@Setter
public class ClockInListF {
    private Integer uid;
    private String studentClass;
    private String name;
}
