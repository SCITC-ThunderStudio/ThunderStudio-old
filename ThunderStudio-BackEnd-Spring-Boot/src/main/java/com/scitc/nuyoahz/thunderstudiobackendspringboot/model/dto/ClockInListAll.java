package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import java.util.Date;

/**
 * 全部打卡记录集合
 *
 * @author Nuyoahz
 * @date 2024/2/6
 */
public record ClockInListAll(
        Integer recordsId
        , Integer uid
        , String studentClass
        , String name, String sex
        , Date submitTime
        , Date updateTime
        , Integer number
        , String logs
        , String picturePath) {
}
