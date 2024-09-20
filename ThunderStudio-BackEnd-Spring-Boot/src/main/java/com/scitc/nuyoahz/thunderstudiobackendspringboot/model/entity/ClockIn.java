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
 * 打卡记录表
 *
 * @author Nuyoahz
 * @date 2023/07/28
 */
@Data
@TableName(value = "clock_in")
public class ClockIn implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 打卡记录id
     */
    @TableId(type = IdType.AUTO)
    private Integer recordsId;
    /**
     * 学生id
     */
    private Integer uid;
    /**
     * 做题数
     */
    private Integer number;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 打卡日志
     */
    private String logs;
    /**
     * 图片路径
     */
    private String picturePath;

    public ClockIn(Integer uid, Integer number, String logs, String picturePath) {
        this.uid = uid;
        this.number = number;
        this.logs = logs;
        this.updateTime = null;
        this.picturePath = picturePath;
    }

    public ClockIn(Integer recordsId, Integer number, String updateTime, String logs, String picturePath) {
        this.recordsId = recordsId;
        this.number = number;
        this.logs = logs;
        this.updateTime = updateTime;
        this.picturePath = picturePath;
    }

    public ClockIn(Integer recordsId, Integer uid, Integer number, String logs, Date submitTime, String updateTime, String picturePath) {
        this.recordsId = recordsId;
        this.uid = uid;
        this.number = number;
        this.logs = logs;
        this.submitTime = submitTime;
        this.updateTime = updateTime;
        this.picturePath = picturePath;
    }
}