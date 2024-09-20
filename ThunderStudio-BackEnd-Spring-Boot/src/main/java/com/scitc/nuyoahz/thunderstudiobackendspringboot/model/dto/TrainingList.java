package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 训练营名单集合
 *
 * @author Nuyoahz
 * @date 2024/05/12
 */
@Getter
@Setter
public class TrainingList {
    private Integer uid;
    private String studentClass;
    private String sex;
    private String name;
    private String email;
    private Integer principal;
}
