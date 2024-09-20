package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 打卡记录集合
 *
 * @author Nuyoahz
 * @date 2024/1/14
 */
@Getter
@Setter
public class ClockInRecord {
    private Integer recordsId;
    public Integer uid;
    public String studentClass;
    private String name;
    private Integer number;
    private String logs;
    private String submitTime;
    private String updateTime;
    private String picturePath;
}
