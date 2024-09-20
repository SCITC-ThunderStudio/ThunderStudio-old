package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

/**
 * 更新密码表单(未登陆)
 *
 * @author Nuyoahz
 * @date 2024/3/15
 */
public record UpdatePasswordFForm(Integer uid, String captcha, String newPassword) {
}