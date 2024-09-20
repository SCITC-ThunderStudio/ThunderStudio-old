package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

/**
 * 更新密码表单(登陆)
 *
 * @author Nuyoahz
 * @date 2024/3/17
 */
public record UpdatePasswordTForm(String oldPassword, String newPassword, String repeatPassword) {
}