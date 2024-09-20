package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

/**
 * 登陆表单
 *
 * @author Nuyoahz
 * @date 2024/2/21
 */
public record LoginForm(Integer uid, String password, Integer isRememberMe) {
}