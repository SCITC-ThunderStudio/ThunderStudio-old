package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 概览数据
 *
 * @author Nuyoahz
 * @date 2024/2/5 19:35
 */
@Getter
@Setter
public class Overview {
    private Integer totalCount;
    private Integer withdrawalCount;
    private Integer inTrainingCount;
    private Integer graduatedCount;
}
