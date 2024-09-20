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
 * 周报记录表
 *
 * @author Nuyoahz
 * @date 2024/02/20
 */
@Data
@TableName(value = "weekly")
public class Weekly implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 周报记录id
     */
    @TableId(type = IdType.AUTO)
    private Integer weeklyId;
    /**
     * 学号
     */
    private Integer uid;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 评价
     */
    private String evaluate;
    /**
     * 周报路径
     */
    private String filePath;

    public Weekly(Integer uid, String fileName, String filePath) {
        this.uid = uid;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Weekly(Integer weeklyId, Integer uid, String fileName, Date submitTime, String filePath, String evaluate) {
        this.weeklyId = weeklyId;
        this.uid = uid;
        this.fileName = fileName;
        this.submitTime = submitTime;
        this.filePath = filePath;
        this.evaluate = evaluate;
    }
}