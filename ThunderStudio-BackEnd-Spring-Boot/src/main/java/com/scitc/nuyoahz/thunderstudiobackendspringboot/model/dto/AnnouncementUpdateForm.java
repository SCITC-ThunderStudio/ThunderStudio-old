package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import java.util.Date;

/**
 * 公告更新表单
 *
 * @author Nuyoahz
 * @date 2024/04/22
 */
public record AnnouncementUpdateForm(
        Integer announcementId
        , String title
        , String content
        , Integer isRemind
        , Date reminderTime) {
}