package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 个人周报记录集合
 *
 * @author Nuyoahz
 * @date 2024/3/19
 */
@Getter
@Setter
public class WeeklyList {
    private Integer weeklyId;
    private String fileName;
    private String submitTime;
    private String filePath;
    private String evaluate;
}
