package com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementAllList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Announcement;

import java.util.List;

/**
 * 针对表【announcement(公告表)】的数据库操作Mapper
 *
 * @author Nuyoahz
 * @date 2024-04-21
 */
public interface AnnouncementMapper extends BaseMapper<Announcement> {
    /**
     * 查询公告
     *
     * @return List<AnnouncementList>
     * @author Nuyoahz
     * @date 2024/4/22
     */
    List<AnnouncementList> queryAnnouncement();

    /**
     * 查询全部公告
     *
     * @return List<AnnouncementAllList>
     * @author Nuyoahz
     * @date 2024/9/17
     */
    List<AnnouncementAllList> queryAnnouncementAll();
}