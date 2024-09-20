package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告表
 *
 * @author Nuyoahz
 * @date 2024-04-21
 */
@TableName(value = "announcement")
@Data
public class Announcement implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 公告id
     */
    @TableId(type = IdType.AUTO)
    private Integer announcementId;
    /**
     * 公告创建人
     */
    private Integer uid;
    /**
     * 公告标题
     */
    private String title;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date creationTime;
    /**
     * 是否提醒
     */
    private Integer isRemind;
    /**
     * 提醒时间
     */
    private Date reminderTime;

    public Announcement(Integer uid, String title, String content, Date creationTime, Integer isRemind, Date reminderTime) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
        this.isRemind = isRemind;
        this.reminderTime = reminderTime;
    }

    public Announcement(Integer announcementId, String title, String content, Integer isRemind, Date reminderTime) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.isRemind = isRemind;
        this.reminderTime = reminderTime;
    }

    public Announcement(Integer uid, String title, String content, Date creationTime) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
    }
}