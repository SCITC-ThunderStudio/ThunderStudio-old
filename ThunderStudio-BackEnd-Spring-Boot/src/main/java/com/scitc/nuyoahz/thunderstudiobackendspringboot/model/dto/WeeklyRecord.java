package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 周报记录集合
 *
 * @author Nuyoahz
 * @date 2024/05/09
 */
@Getter
@Setter
public class WeeklyRecord {
    private Integer weeklyId;
    private Integer uid;
    public String studentClass;
    private String name;
    private String fileName;
    private String submitTime;
    private String evaluate;
    private String filePath;
}