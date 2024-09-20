package com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper.AnnouncementMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementAllList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.AnnouncementUpdateForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Announcement;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.AnnouncementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对表【announcement(公告表)】的数据库操作Service实现
 *
 * @author Nuyoahz
 * @date 2024-04-21
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
        implements AnnouncementService {

    @Override
    public List<AnnouncementList> getAnnouncement() {
        return baseMapper.queryAnnouncement();
    }

    @Override
    public Map<String, Object> getAnnouncementList(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AnnouncementAllList> announcementAllLists = baseMapper.queryAnnouncementAll();
        PageInfo<AnnouncementAllList> pageInfo = new PageInfo<>(announcementAllLists);
        return new HashMap<>(4) {
            {
                put("total", pageInfo.getTotal());
                put("pageSize", pageInfo.getSize());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", announcementAllLists);
            }
        };
    }

    @Override
    public void announcement(Integer uid, AnnouncementForm announcementForm) {
        baseMapper.insert(
                new Announcement(
                        uid
                        , announcementForm.title()
                        , announcementForm.content()
                        , new Date()
                )
        );
    }

    @Override
    public void updateAnnouncement(AnnouncementUpdateForm announcementUpdateForm) {
        int state = baseMapper.updateById(
                new Announcement(
                        announcementUpdateForm.announcementId()
                        , announcementUpdateForm.title()
                        , announcementUpdateForm.content()
                        , announcementUpdateForm.isRemind()
                        , announcementUpdateForm.reminderTime()
                )
        );
        if (state != 1) {
            ApiException.setError(StatusCodeConstant.UPDATE_ANNOUNCEMENT_ERROR);
        }
    }

    @Override
    public void deleteAnnouncement(Integer announcementId) {
        int state = baseMapper.deleteById(announcementId);
        if (state != 1) {
            ApiException.setError(StatusCodeConstant.DELETE_ANNOUNCEMENT_ERROR);
        }
    }
}