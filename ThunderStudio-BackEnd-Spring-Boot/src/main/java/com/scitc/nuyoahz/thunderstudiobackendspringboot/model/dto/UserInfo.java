package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 *
 * @author Nuyoahz
 * @date 2024/3/19
 */
@Getter
@Setter
public class UserInfo {
    private Integer uid;
    private String studentClass;
    private String name;
    private String email;
    private String joinDate;
    private Integer questionNumber;
    private String authority;
    private String avatarPath;
    private Integer ranking;
}