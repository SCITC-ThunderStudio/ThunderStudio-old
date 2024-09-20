package com.scitc.nuyoahz.thunderstudiobackendspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementUpdateForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Announcement;

import java.util.List;
import java.util.Map;

/**
 * 针对表【announcement(公告表)】的数据库操作Service
 *
 * @author Nuyoahz
 * @date 2024-04-21
 */
public interface AnnouncementService extends IService<Announcement> {
    /**
     * 获取公告
     *
     * @return List<AnnouncementList>
     * @author Nuyoahz
     * @date 2024/4/22
     */
    List<AnnouncementList> getAnnouncement();

    /**
     * 获取全部公告
     *
     * @return Map<Object>
     * @author Nuyoahz
     * @date 2024/9/17
     */
    Map<String, Object> getAnnouncementList(Integer currentPage, Integer pageSize);

    /**
     * 发布公告
     *
     * @param uid              用户id
     * @param announcementForm 公告表单
     * @author Nuyoahz
     * @date 2024/4/22
     */
    void announcement(Integer uid, AnnouncementForm announcementForm);

    /**
     * 更新公告
     *
     * @param announcementUpdateForm 更新公告表单
     * @author Nuyoahz
     * @date 2024/4/22
     */
    void updateAnnouncement(AnnouncementUpdateForm announcementUpdateForm);

    /**
     * 删除公告
     *
     * @param announcementId 公告id
     * @author Nuyoahz
     * @date 2024/4/22
     */
    void deleteAnnouncement(Integer announcementId);
}
