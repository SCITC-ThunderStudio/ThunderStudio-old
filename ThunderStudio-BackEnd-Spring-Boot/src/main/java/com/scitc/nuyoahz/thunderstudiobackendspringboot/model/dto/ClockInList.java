package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 打卡记录集合
 *
 * @author Nuyoahz
 * @date 2024/3/18
 */
@Getter
@Setter
public class ClockInList {
    private Integer recordsId;
    private Integer number;
    private String submitTime;
    private String updateTime;
    private String logs;
    private String picturePath;
}