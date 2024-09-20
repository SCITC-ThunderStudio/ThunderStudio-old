package com.scitc.nuyoahz.thunderstudiobackendspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 针对表【student(学生表)】的数据库操作Service
 *
 * @author Nuyoahz
 * @date 2024/01/11
 */
public interface UserService extends IService<User> {
    /**
     * 用户登陆
     *
     * @param loginForm 登陆表单
     * @return String
     * @author Nuyoahz
     * @date 2024/01/11
     */
    String login(LoginForm loginForm);

    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return UserInfo
     * @author Nuyoahz
     * @date 2024/03/13
     */
    UserInfo getUserInfo(Integer uid);

    /**
     * 获取路由
     *
     * @param authority 权限
     * @return List<RouterList>
     * @author Nuyoahz
     * @date 2024/01/11
     */
    List<RouterList> getRouter(Integer authority);

    /**
     * 获取验证码
     *
     * @param uid   用户id
     * @param email 邮箱
     * @author Nuyoahz
     * @date 2024/01/11
     */
    void getCaptcha(Integer uid, String email);

    /**
     * 未登录下修改密码
     *
     * @param updatePasswordFForm 更新密码表单
     * @author Nuyoahz
     * @date 2024/01/12
     */
    void updatePassword(UpdatePasswordFForm updatePasswordFForm);

    /**
     * 登录下修改密码
     *
     * @param updatePasswordTForm 更新密码表单
     * @param uid                 用户id
     * @author Nuyoahz
     * @date 2024/01/12
     */
    void updatePassword(UpdatePasswordTForm updatePasswordTForm, Integer uid);

    /**
     * 更新头像
     *
     * @param uid        用户id
     * @param avatarFile 头像文件
     * @author Nuyoahz
     * @date 2024/03/18
     */
    void updateAvatar(Integer uid, MultipartFile avatarFile);

    /**
     * 获取未打卡人员
     *
     * @param option 选项,0今日未打卡人员,1昨日未打卡人员
     * @return List<ClockInListAllF>
     * @author Nuyoahz
     * @date 2024/01/14
     */
    List<ClockInListF> getClockInListF(Integer option);

    /**
     * 获取未提交周报人员名单
     *
     * @param option 选项 0今日,1全部
     * @return List<WeeklyListF>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    List<WeeklyListF> getWeeklyListF(Integer option);

    /**
     * 获取概览
     *
     * @return Overview
     * @author Nuyoahz
     * @date 2024/02/05
     */
    Overview getOverview();

    /**
     * 获取做题数前十排名
     *
     * @return List<Rank>
     * @author Nuyoahz
     * @date 2024/02/05
     */
    List<Rank> getRank();

    /**
     * 获取个人负责人员
     *
     * @param uid         用户id
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return Map<Object>
     * @author Nuyoahz
     * @date 2024/4/18
     */
    Map<String, Object> getTrainee(Integer uid, Integer currentPage, Integer pageSize);

    /**
     * 更新人员状态
     *
     * @param personnelStateForm 人员状态表单
     * @author Nuyoahz
     * @date 2024/4/19
     */
    void updateResponseState(PersonnelStateForm personnelStateForm);

    /**
     * 重置人员打卡状态
     *
     * @author Nuyoahz
     * @date 2024/5/10
     */
    void resetClockInState();

    /**
     * 重置人员周报状态
     *
     * @author Nuyoahz
     * @date 2024/5/10
     */
    void resetWeeklyState();

    /**
     * 上传训练营名单接口
     *
     * @param trainingListFile 训练营名单文件
     * @author Nuyoahz
     * @date 2024/5/12
     */
    void uploadTrainingList(MultipartFile trainingListFile);

    /**
     * 获取人员名单接口
     *
     * @param pageSize   每页记录数
     * @param pageNumber 页码
     * @return Map<Object>
     * @author Nuyoahz
     * @date 2024/5/12
     */
    Map<String, Object> getPersonnelList(Integer pageSize, Integer pageNumber);

    void deleteUser(Integer uid);

    void updateUserInfo(UpdateUserInfoReqFormAdmin userInfoForm);
}
