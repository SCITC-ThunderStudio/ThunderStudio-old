package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 学生表
 *
 * @author Nuyoahz
 * @date 2024/01/11
 */
@Data
@TableName(value = "user")
public class User implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId
    private Integer uid;
    /**
     * 班级
     */
    private String studentClass;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 加入日期
     */
    private Date joinDate;
    /**
     * 做题数
     */
    private Integer questionNumber;
    /**
     * 负责人
     */
    private Integer principal;
    /**
     * 在训状态
     * 退出0 在训1 毕业2
     */
    private Integer state;
    /**
     * 权限
     * 管理员0 负责人1 学生2
     */
    private Integer authority;
    /**
     * 打卡状态
     * 未打卡0 打卡1
     */
    private Integer clockInState;
    /**
     * 周报状态
     * 未提交0 提交1
     */
    private Integer weeklyState;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像路径
     */
    private String avatarPath;

    public User() {
    }

    public User(Integer uid, String studentClass, String sex, String name, Integer authority, Integer state) {
        this.uid = uid;
        this.studentClass = studentClass;
        this.name = name;
        this.authority = authority;
        this.state = state;
    }
}