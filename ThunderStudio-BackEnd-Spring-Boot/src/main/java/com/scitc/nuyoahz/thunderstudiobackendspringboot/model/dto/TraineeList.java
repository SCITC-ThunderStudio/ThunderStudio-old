package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 个人负责人员信息集合
 *
 * @author Nuyoahz
 * @date 2024/04/18
 */
@Getter
@Setter
public class TraineeList {
    private Integer uid;
    private String studentClass;
    private String name;
    private String sex;
    private Integer state;
    private Integer clockInState;
    private Integer weeklyState;
}
