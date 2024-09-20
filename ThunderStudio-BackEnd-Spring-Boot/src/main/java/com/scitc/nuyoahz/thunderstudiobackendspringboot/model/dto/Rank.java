package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 排名数据
 *
 * @author Nuyoahz
 * @date 2024/2/5 19:45
 */
@Getter
@Setter
public class Rank {
    private Integer uid;
    private String studentClass;
    private String name;
    private Integer questionNumber;
    private String avatarPath;
}