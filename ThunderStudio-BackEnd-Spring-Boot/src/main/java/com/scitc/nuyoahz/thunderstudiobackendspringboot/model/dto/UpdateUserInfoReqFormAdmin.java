package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

public record UpdateUserInfoReqFormAdmin(Integer uid, String studentClass, String sex, String name, Integer authority,
                                         Integer state) {
}
