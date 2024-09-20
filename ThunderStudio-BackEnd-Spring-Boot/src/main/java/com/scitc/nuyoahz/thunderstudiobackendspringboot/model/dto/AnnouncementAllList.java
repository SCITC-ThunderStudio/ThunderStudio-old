package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Nuyoahz
 * @date 2024/09/17
 */
@Getter
@Setter
public class AnnouncementAllList {
    private Integer announcementId;
    private String name;
    private String title;
    private String content;
    private String creationTime;
}
