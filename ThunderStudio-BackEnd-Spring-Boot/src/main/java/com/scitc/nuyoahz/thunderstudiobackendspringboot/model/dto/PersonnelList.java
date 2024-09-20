package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 人员名单集合
 *
 * @author Nuyoahz
 * @date 2024/05/12
 */
@Getter
@Setter
public class PersonnelList {
    private Integer uid;
    private String studentClass;
    private String sex;
    private String name;
    private Integer questionNumber;
    private String principal;
    private Integer authority;
    private Integer state;
}
