package com.scitc.nuyoahz.thunderstudiobackendspringboot.constant;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.RouterList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 系统常量
 *
 * @author Nuyoahz
 * @date 2024/1/9 12:59
 */
public class SystemConstant {
    /**
     * 概览
     */
    public static final RouterList OVER_VIEW = new RouterList(1, "overView", "OverView", new HashMap<>(2) {
        {
            put("icon", "Menu");
            put("title", "概览");
        }
    });

    /**
     * 打卡管理
     */
    public static final RouterList CLOCK_IN_MANAGE_VIEW = new RouterList(2, "clockInManage", "ClockInManageView", new HashMap<>(2) {
        {
            put("icon", "Tickets");
            put("title", "打卡管理");
        }
    });

    /**
     * 周报管理
     */
    public static final RouterList WEEKLY_MANAGE_VIEW = new RouterList(2, "weeklyManage", "WeeklyManageView", new HashMap<>(2) {
        {
            put("icon", "Memo");
            put("title", "周报管理");
        }
    });

    /**
     * 打卡概览
     */
    public static final RouterList CLOCK_IN_OVER_VIEW = new RouterList(2, "clockInOver", "ClockInOverView", new HashMap<>(2) {
        {
            put("icon", "List");
            put("title", "打卡概览");
        }
    });

    /**
     * 周报概览
     */
    public static final RouterList WEEKLY_OVER_VIEW = new RouterList(2, "weeklyOver", "WeeklyOverView", new HashMap<>(2) {
        {
            put("icon", "Management");
            put("title", "周报概览");
        }
    });

    /**
     * 训员管理
     */
    public static final RouterList TRAINING_MANAGE_VIEW = new RouterList(3, "trainingManage", "TrainingManageView", new HashMap<>(2) {
        {
            put("icon", "Avatar");
            put("title", "训员管理");
        }
    });

    /**
     * 人员管理
     */
    public static final RouterList PERSONNEL_MANAGE_VIEW = new RouterList(3, "personnelManage", "PersonnelManageView", new HashMap<>(2) {
        {
            put("icon", "UserFilled");
            put("title", "人员管理");
        }
    });

    /**
     * 人员管理
     */
    public static final RouterList ANNOUNCEMENT_MANAGEMENT_VIEW = new RouterList(4, "announcementManagement", "AnnouncementManagementView", new HashMap<>(2) {
        {
            put("icon", "ChatLineRound");
            put("title", "公告管理");
        }
    });

    public static final List<String> ADMIN_PATH = new ArrayList<>() {
        {
            add("/user/login");
            add("/user/sendEmail");
            add("/user/loginOut");
            add("/user/getRouter");
            add("/clockInManage/clockIn");
            add("/clockInManage/clockInOver");
            add("/clockInManage/clockInList");
            add("/clockInManage/clockInEdit");
            add("/clockInManage/clockInDelete");
            add("/clockInManage/clockInExport");
        }
    };

    public static final List<String> PERSON_IN_CHARGE_PATH = new ArrayList<>() {
    };

    public static final List<String> EVERY_MAN = new ArrayList<>() {
        {
            add("/user/login");
            add("/user/sendEmail");
            add("/user/loginOut");
            add("/user/getRouter");
            add("/clockInManage/clockIn");
            add("/clockInManage/clockInOver");
            add("/clockInManage/clockInList");
            add("/clockInManage/clockInEdit");
            add("/clockInManage/clockInDelete");
            add("/clockInManage/clockInExport");
        }
    };

    /**
     * 请求白名单
     */
    public static final List<String> REQUEST_WHITEE_LIST = new ArrayList<>() {
        {
            add("/public/getRSAPublicKey");
            add("/public/getCaptcha");
            add("/public/getOverview");
            add("/public/getRank");
            add("/public/getAnnouncement");
        }
    };

    /**
     * 时间格式
     */
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyy-MM-dd";

    /**
     * 会话过期时间 30天
     */
    public static final Long EXPIRE_DATE_THIRTY_MOON = (long) (30 * 24 * 60 * 60);
    /**
     * 会话过期时间1小时
     */
    public static final Long EXPIRE_DATE_THIRTY_HOUR = (long) 30 * 24 * 60 * 60;

    /**
     * HTTP头字段
     */
    public static final String HEART_ENCRYPT = "Encrypt";
    public static final String HEART_SESSION = "Session-Id";
    public static final String HEART_KEY = "Key";
    public static final String HEART_IV = "Iv";

    /**
     * 请求参数是否加密
     */
    public static final String IS_ENCRYPT = "1";

    /**
     * 邮箱格式
     */
    public static final String EMAIL_FORMAT = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * Word文件格式
     */
    public static final List<String> WORD_FORMAT = new ArrayList<>(Arrays.asList("application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));

    /**
     * Excel文件格式
     */
    public static final List<String> EXCEL_FORMAT = new ArrayList<>(
            Arrays.asList(
                    ".xls"
                    , ".xlsx"
                    , "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    , "application/vnd.ms-excel"
            )
    );

    /**
     * 必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-15之间
     */
    public static final String PASSWORD_FORMAT = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";

    /**
     * 图片格式
     */
    public static final List<String> IMAGE_FORMAT = new ArrayList<>(Arrays.asList("image/png", "image/jpeg"));

    /**
     * 打卡图片存储目录
     */
    public static final String PUNCH_IN_PICTURE_DIR = "PunchInPicture/";

    /**
     * 打卡图片网络路径前缀
     */
    public static final String IMG_NETWORK_PATH = "picture/";

    /**
     * 打卡图片静态映射路径
     */
    public static final String IMG_MAPPING_PATH = "file:" + CommonUtils.getJarAbsolutePath() + PUNCH_IN_PICTURE_DIR;

    /**
     * 周报文件存储目录
     */
    public static final String WEEKLY_DOCUMENTS_DIR = "WeeklyDocuments/";

    /**
     * 周报文件网络路径
     */
    public static final String WEEKLY_NETWORK_PATH = "weekly/";

    /**
     * 周报文件静态映射路径
     */
    public static final String WEEKLY_MAPPING_PATH = "file:" + CommonUtils.getJarAbsolutePath() + WEEKLY_DOCUMENTS_DIR;

    /**
     * 头像存储目录
     */
    public static final String AVATAR_DIR = "Avatar/";

    /**
     * 头像文件网络路径
     */
    public static final String AVATAR_NETWORK_PATH = "avatar/";

    /**
     * 头像文件静态映射路径
     */
    public static final String AVATAR_MAPPING_PATH = "file:" + CommonUtils.getJarAbsolutePath() + AVATAR_DIR;

    /**
     * 默认头像图片名称
     */
    public static final String DEFAULT_AVATAR_NAME = "defaultAvatar.png";
    public static final Integer ADMIN_UID = 20163021;
}
