package com.scitc.nuyoahz.thunderstudiobackendspringboot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义响应码类
 *
 * @author Nuyoahz
 * @date 2024/1/9 13:00
 */
@Getter
@AllArgsConstructor
public enum StatusCodeConstant {
    // 通用响应码
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),

    LOGIN_SUCCESS(600, "登陆成功"),
    LOGIN_OUT_SUCCESS(601, "登出成功"),
    LOGIN_OUT_ERROR(602, "登出失败"),
    LOGIN_ERROR(603, "账户不存在或密码错误"),
    ACCOUNT_FORMAT_ERROR(604, "账户格式错误"),
    ACCOUNT_NOT_FOUND_ERROR(605, "账户不存在"),
    ACCOUNT_EXPIRATION(606, "账户失效"),

    PASSWORD_UPDATE_SUCCESS(610, "修改密码成功"),
    PASSWORD_UPDATE_ERROR(611, "修改密码失败"),
    PASSWORD_FORMAT_ERROR(612, "密码格式错误"),
    OLD_PASSWORD_ERROR(613, "旧密码错误"),

    EMAIL_ERROR(615, "邮箱不匹配"),
    EMAIL_UPDATE_SUCCESS(616, "修改邮箱成功"),
    EMAIL_UPDATE_ERROR(617, "修改邮箱失败"),
    EMAIL_FORMAT_ERROR(618, "邮箱格式错误"),

    CAPTCHA_ERROR(620, "验证码错误"),
    CAPTCHA_EXPIRATION_ERROR(621, "验证码失效"),
    CAPTCHA_SENT_SUCCESS(622, "验证码发送成功"),
    CAPTCHA_SENT_ERROR(623, "验证码发送失败"),

    WEEKLY_COMMENTS_SUCCESS(625, "周报评价成功"),
    WEEKLY_COMMENTS_ERROR(626, "周报评价失败"),

    USER_INFO_SUCCESS(630, "用户信息获取成功"),
    USER_INFO_NOT_FOUND_ERROR(631, "用户信息不存在"),

    TRAINER_IMPORT_SUCCESS(635, "训员导入成功"),
    TRAINER_IMPORT_ERROR(636, "训员导入失败失败"),

    WEEKLY_SUBMIT_SUCCESS(640, "周报提交成功"),
    WEEKLY_SUBMIT_ERROR(641, "周报提交失败"),
    WEEKLY_DELETE_SUCCESS(642, "周报删除成功"),
    WEEKLY_DELETE_ERROR(643, "周报删除失败"),
    WEEKLY_UPDATE_SUCCESS(644, "周报更新成功"),
    WEEKLY_UPDATE_ERROR(645, "周报更新失败"),
    WEEKLY_NOT_FOUND_ERROR(646, "周报记录不存在"),

    CLOCK_IN_SUBMIT_SUCCESS(650, "打卡记录提交成功"),
    CLOCK_IN_SUBMIT_ERROR(651, "打卡记录提交失败"),
    CLOCK_IN_DELETE_SUCCESS(652, "打卡记录删除成功"),
    CLOCK_IN_DELETE_ERROR(653, "打卡记录删除失败"),
    CLOCK_IN_UPDATE_SUCCESS(654, "打卡记录更新成功"),
    CLOCK_IN_UPDATE_ERROR(655, "打卡记录更新失败"),
    CLOCK_IN_REMINDER_SUCCESS(656, "打卡提醒成功"),
    CLOCK_IN_REMINDER_ERROR(657, "打卡提醒失败"),

    STATE_UPDATE_SUCCESS(665, "人员状态修改成功"),
    STATE_UPDATE_ROOT_ERROR(666, "禁止修改人员状态"),
    STATE_UPDATE_ERROR(667, "人员状态修改失败"),
    STATE_QUERY_ERROR(668, "人员状态查询异常"),

    DELETE_USER_ERROR(669, "人员删除失败"),

    UPDATE_AVATAR_SUCCESS(670, "头像更新成功"),
    UPDATE_AVATAR_ERROR(671, "头像更新失败"),

    ANNOUNCEMENT_SUCCESS(675, "公告发布成功"),
    ANNOUNCEMENT_ERROR(676, "公告发布失败"),
    DELETE_ANNOUNCEMENT_SUCCESS(678, "删除公告成功"),
    DELETE_ANNOUNCEMENT_ERROR(679, "删除公告失败"),
    UPDATE_ANNOUNCEMENT_SUCCESS(680, "更新公告成功"),
    UPDATE_ANNOUNCEMENT_ERROR(681, "更新公告失败"),

    SYSTEM_ERROR(900, "系统内部错误错误"),
    NULL_PARAMETER_ERROR(901, "空参数异常"),
    COOKIE_ERROR(902, "Cookie获取异常"),
    EMPTY_FILE(903, "空文件"),
    FILE_FORMAT_ERROR(904, "文件格式错误"),
    FILE_SIZE_ERROR(905, "文件超出限制"),
    FOLDER_ERROR(906, "文件夹创建失败"),
    FILE_WRITE_ERROR(907, "文件写入失败"),
    FILE_READING_ERROR(908, "文件读取异常"),
    INSUFFICIENT_PERMISSIONS(909, "权限不足");

    private final Integer code;
    private final String message;
}
