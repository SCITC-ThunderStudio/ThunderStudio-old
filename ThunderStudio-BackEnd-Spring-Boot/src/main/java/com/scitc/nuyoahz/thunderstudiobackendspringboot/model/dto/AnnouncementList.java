package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 公告集合
 *
 * @author Nuyoahz
 * @date 2024/04/22
 */
@Getter
@Setter
public class AnnouncementList {
    private String title;
    private String content;
    private String creationTime;
}
