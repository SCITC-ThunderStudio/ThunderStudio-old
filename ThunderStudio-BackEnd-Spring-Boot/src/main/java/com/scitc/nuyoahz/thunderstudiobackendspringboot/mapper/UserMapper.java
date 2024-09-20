package com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 针对表【student(学生表)】的数据库操作Mapper
 *
 * @author Nuyoahz
 * @date 2024/01/11
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户信息
     *
     * @param uid 用户id
     * @return UserInfo
     * @author Nuyoahz
     * @date 2024/03/13
     */
    UserInfo queryUserInfo(@Param("uid") Integer uid, @Param("host") String host);

    /**
     * 更新密码
     *
     * @param uid         用户id
     * @param newPassword 新密码
     * @return Integer
     * @author Nuyoahz
     * @date 2024/01/12
     */
    Integer updatePassword(@Param("uid") Integer uid, @Param("newPassword") String newPassword);

    /**
     * 更新头像路径
     *
     * @param uid        用户id
     * @param avatarPath 头像路径
     * @return Integer
     * @author Nuyoahz
     * @date 2024/03/18 15:03
     */
    Integer updateAvatarPath(@Param("uid") Integer uid, @Param("avatarPath") String avatarPath);

    /**
     * 查询今日未打卡人员
     *
     * @return List<ClockInListAllF>
     * @author Nuyoahz
     * @date 2024/01/14
     */
    List<ClockInListF> queryClockInListF();

    /**
     * 查询本周未提交周报人员名单
     *
     * @return List<WeeklyListF>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    List<WeeklyListF> queryWeeklyListF();

    /**
     * 更新做题数,默认为加
     *
     * @param uid            用户id
     * @param questionNumber 做题数
     * @return Integer
     * @author Nuyoahz
     * @date 2024/01/14
     */
    Integer updateQuestionNumber(@Param("uid") Integer uid, @Param("questionNumber") Integer questionNumber);

    /**
     * 查询概览
     *
     * @return Overview
     * @author Nuyoahz
     * @date 2024/02/05
     */
    Overview queryOverview();

    /**
     * 查询做题数前十排名
     *
     * @return List<Rank>
     * @author Nuyoahz
     * @date 2024/02/05
     */
    List<Rank> queryRank();

    /**
     * 更新打卡状态
     *
     * @param uid          用户用id
     * @param clockInState 打卡状态
     * @return Integer
     * @author Nuyoahz
     * @date 2024/02/22
     */
    Integer updateClockInState(@Param("uid") Integer uid, @Param("clockInState") Integer clockInState);

    /**
     * 更新周报状态
     *
     * @param uid         学号
     * @param weeklyState 周报提交状态
     * @return Integer
     * @author Nuyoahz
     * @date 2024/02/22
     */
    Integer updateWeeklyState(@Param("uid") Integer uid, @Param("weeklyState") Integer weeklyState);

    /**
     * 查询个人负责人员
     *
     * @param uid 用户id
     * @return List<TraineeList>
     * @author Nuyoahz
     * @date 2024/4/18
     */
    List<TraineeList> queryTrainee(@Param("uid") Integer uid);

    /**
     * 更新人员状态
     *
     * @param uid            用户id
     * @param personnelState 人员状态
     * @return Integer
     * @author Nuyoahz
     * @date 2024/4/19
     */
    Integer updatePersonnelState(@Param("uid") Integer uid, @Param("personnelState") Integer personnelState);

    /**
     * 更新人员打卡状态
     *
     * @return Integer
     * @author Nuyoahz
     * @date 2024/5/10
     */
    Integer updateClockInStateAll();

    /**
     * 更新人员周报状态
     *
     * @return Integer
     * @author Nuyoahz
     * @date 2024/5/10
     */
    Integer updateWeeklyStateAll();

    /**
     * 查询人员名单
     *
     * @return List<PersonnelList>
     * @author Nuyoahz
     * @date 2024/5/12
     */
    List<PersonnelList> queryPersonnelList();

    /**
     * 插入训练营名单
     *
     * @param trainingList 训练营名单
     * @return Integer
     * @author Nuyoahz
     * @date 2024/5/13
     */
    Integer insertTrainingList(@Param("trainingList") List<TrainingList> trainingList);
}