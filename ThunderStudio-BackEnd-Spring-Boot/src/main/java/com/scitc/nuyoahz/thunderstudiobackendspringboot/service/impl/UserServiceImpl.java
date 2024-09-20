package com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper.UserMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.User;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.UserService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.DESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.EmailUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 针对表【student(学生表)】的数据库操作Service实现
 *
 * @author Nuyoahz
 * @date 2024/01/11
 */
@Service
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Value("${host.url}")
    private String host;

    @Override
    public String login(LoginForm loginForm) {
        User user = baseMapper.selectById(loginForm.uid());
        if (user == null || !DESUtils.encrypt(loginForm.password()).equals(user.getPassword())) {
            ApiException.setError(StatusCodeConstant.LOGIN_ERROR);
        }
        // 如果用户状态为退出,则不能登录
        if (user.getState() == 0) {
            ApiException.setError(StatusCodeConstant.ACCOUNT_EXPIRATION);
        }
        // 用户UID + 权限信息
        String userInfo = user.getUid() + ":" + user.getAuthority();
        // 随机生成16的会话ID
        String sessionId = RandomUtil.randomString(16);
        // 将会话ID,用户信息存入Redis
        RedisUtils.setKey(sessionId, userInfo, loginForm.isRememberMe() == 1 ? SystemConstant.EXPIRE_DATE_THIRTY_MOON : SystemConstant.EXPIRE_DATE_THIRTY_HOUR);
        log.info("用户 {} 登陆成功, 会话ID为:{}", user.getName(), sessionId);
        return sessionId;
    }

    @Override
    public UserInfo getUserInfo(Integer uid) {
        UserInfo userInfo = baseMapper.queryUserInfo(uid, host + '/' + SystemConstant.AVATAR_NETWORK_PATH);
        if (userInfo == null) {
            ApiException.setError(StatusCodeConstant.USER_INFO_NOT_FOUND_ERROR);
        }
        return userInfo;
    }

    @Override
    public List<RouterList> getRouter(Integer authority) {
        return switch (authority) {
            case 0 ->
                    Arrays.asList(SystemConstant.OVER_VIEW, SystemConstant.CLOCK_IN_OVER_VIEW, SystemConstant.WEEKLY_OVER_VIEW, SystemConstant.PERSONNEL_MANAGE_VIEW, SystemConstant.ANNOUNCEMENT_MANAGEMENT_VIEW);
            case 1 ->
                    Arrays.asList(SystemConstant.OVER_VIEW, SystemConstant.CLOCK_IN_MANAGE_VIEW, SystemConstant.WEEKLY_MANAGE_VIEW, SystemConstant.TRAINING_MANAGE_VIEW, SystemConstant.ANNOUNCEMENT_MANAGEMENT_VIEW);
            default ->
                    Arrays.asList(SystemConstant.OVER_VIEW, SystemConstant.CLOCK_IN_MANAGE_VIEW, SystemConstant.WEEKLY_MANAGE_VIEW);
        };
    }

    @Override
    public void getCaptcha(Integer uid, String email) {
        User user = baseMapper.selectById(uid);
        if (user == null) {
            ApiException.setError(StatusCodeConstant.ACCOUNT_NOT_FOUND_ERROR);
        }
        if (!user.getEmail().equals(email)) {
            ApiException.setError(StatusCodeConstant.EMAIL_ERROR);
        }
        EmailUtils.sendEmail(String.valueOf(uid), email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordFForm updatePasswordFForm) {
        Integer uid = updatePasswordFForm.uid();
        // 获取验证码
        String captcha = (String) RedisUtils.getKey(String.valueOf(uid));
        if (captcha == null) {
            ApiException.setError(StatusCodeConstant.CAPTCHA_EXPIRATION_ERROR);
        }
        if (!captcha.equals(updatePasswordFForm.captcha())) {
            ApiException.setError(StatusCodeConstant.CAPTCHA_ERROR);
        }
        if (baseMapper.updatePassword(uid, DESUtils.encrypt(updatePasswordFForm.newPassword())) != 1) {
            ApiException.setError(StatusCodeConstant.PASSWORD_UPDATE_ERROR);
        }
        // 修改密码成功后删除Redis中的验证码
        if (!RedisUtils.deleteKey(String.valueOf(uid))) {
            log.warn("删除Redis中的验证码失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordTForm updatePasswordTForm, Integer uid) {
        User user = baseMapper.selectById(uid);
        if (!DESUtils.encrypt(updatePasswordTForm.oldPassword()).equals(user.getPassword())) {
            ApiException.setError(StatusCodeConstant.OLD_PASSWORD_ERROR);
        }
        if (baseMapper.updatePassword(uid, DESUtils.encrypt(updatePasswordTForm.newPassword())) != 1) {
            ApiException.setError(StatusCodeConstant.PASSWORD_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(Integer uid, MultipartFile avatarFile) {
        // 获取jar包运行路径
        String jarPath = CommonUtils.getJarAbsolutePath();
        // 头像文件绝对路径 jar包运行路径 + "Avatar/"
        File absolutePath = new File(jarPath + SystemConstant.AVATAR_DIR);
        // 文件前缀
        String prefix = IdUtil.randomUUID().toUpperCase();
        // 文件后缀
        String suffix = Objects.requireNonNull(avatarFile.getOriginalFilename()).substring(avatarFile.getOriginalFilename().lastIndexOf("."));
        // 新文件名
        String newFileName = prefix + suffix;
        // 准备文件放入文件夹中
        File newFile = new File(absolutePath, newFileName);
        // 获取用户信息
        User user = baseMapper.selectById(uid);
        // 用户是否存在
        if (user == null) {
            ApiException.setError(StatusCodeConstant.ACCOUNT_NOT_FOUND_ERROR);
        }
        // 判断更改前的头像是否为默认头像
        if (avatarFile.getName().equals(SystemConstant.DEFAULT_AVATAR_NAME)) {
            if (baseMapper.updateAvatarPath(uid, newFileName) != 1) {
                ApiException.setError(StatusCodeConstant.UPDATE_AVATAR_ERROR);
            }
            try {
                // 将文件持久化
                avatarFile.transferTo(newFile);
            } catch (IOException e) {
                log.error("新头像文件持久化失败 {}", e.getMessage());
                ApiException.setError(StatusCodeConstant.UPDATE_AVATAR_ERROR);
            }
        } else {
            if (baseMapper.updateAvatarPath(uid, newFileName) != 1) {
                ApiException.setError(StatusCodeConstant.UPDATE_AVATAR_ERROR);
            }
            // 旧文件文件的绝对路径
            String oldAbsolutePath = jarPath + SystemConstant.AVATAR_DIR + user.getAvatarPath();
            // 新文件文件的绝对路径
            String newAbsolutePath = jarPath + SystemConstant.AVATAR_DIR + newFileName;
            try {
                // 头像文件持久化
                avatarFile.transferTo(newFile);
                if (!new File(oldAbsolutePath).delete()) {
                    new File(newAbsolutePath).delete();
                    log.error("用户 {} 旧头像文件删除失败,文件名 {}", user.getUid(), user.getAvatarPath());
                    ApiException.setError(StatusCodeConstant.UPDATE_AVATAR_ERROR);
                }
            } catch (IOException e) {
                log.error("新头像文件持久化失败 {}", e.getMessage());
                ApiException.setError(StatusCodeConstant.UPDATE_AVATAR_ERROR);
            }
        }
    }

    @Override
    public List<ClockInListF> getClockInListF(Integer option) {
        if (option == 0) {
            return baseMapper.queryClockInListF();
        } else {
            Object clockInListF = RedisUtils.getKey("ClockInListF");
            return JSONUtil.toList((String) clockInListF, ClockInListF.class);
        }
    }

    @Override
    public List<WeeklyListF> getWeeklyListF(Integer option) {
        if (option == 0) {
            return baseMapper.queryWeeklyListF();
        } else {
            Object weeklyListF = RedisUtils.getKey("weeklyListF");
            return JSONUtil.toList((String) weeklyListF, WeeklyListF.class);
        }
    }

    @Override
    public Overview getOverview() {
        return baseMapper.queryOverview();
    }

    @Override
    public List<Rank> getRank() {
        return baseMapper.queryRank();
    }

    @Override
    public Map<String, Object> getTrainee(Integer uid, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<TraineeList> traineeLists = baseMapper.queryTrainee(uid);
        PageInfo<TraineeList> pageInfo = new PageInfo<>(traineeLists);
        return new HashMap<>(3) {
            {
                put("total", pageInfo.getTotal());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", traineeLists);
            }
        };
    }

    @Override
    public void updateResponseState(PersonnelStateForm personnelStateForm) {
        if (baseMapper.updatePersonnelState(personnelStateForm.uid(), personnelStateForm.personnelState()) != 1) {
            ApiException.setError(StatusCodeConstant.STATE_UPDATE_ERROR);
        }
    }

    @Override
    public void resetClockInState() {
        log.info("更新打卡状态记录条数 {}, 总计 {}", baseMapper.updateClockInStateAll(), getOverview().getInTrainingCount());
    }

    @Override
    public void resetWeeklyState() {
        log.info("更新周报状态记录条数 {}, 总计 {}", baseMapper.updateWeeklyStateAll(), getOverview().getInTrainingCount());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadTrainingList(MultipartFile trainingListFile) {
        // 获取上传文件输入流
        InputStream inputStream = null;
        try {
            inputStream = trainingListFile.getInputStream();
        } catch (IOException e) {
            log.error("训练营名单读取异常: {}", e.getMessage());
            ApiException.setError(StatusCodeConstant.FILE_READING_ERROR);
        }
        // 读取指定sheet,设置字段名标题别名
        ExcelReader excelReader = ExcelUtil.getReader(inputStream, 0);
        excelReader.addHeaderAlias("学号", "uid");
        excelReader.addHeaderAlias("班级", "studentClass");
        excelReader.addHeaderAlias("性别", "sex");
        excelReader.addHeaderAlias("姓名", "name");
        excelReader.addHeaderAlias("邮箱", "email");
        excelReader.addHeaderAlias("负责人学号", "principal");
        // 读取数据,指定标题行和起始数据行,结束行,转换为对象
        List<TrainingList> trainingList = excelReader.read(0, 3, Integer.MAX_VALUE, TrainingList.class);
        Integer i = baseMapper.insertTrainingList(trainingList);
        log.info("导入训员 总计: {} 成功: {}", trainingList.size(), i);
        if (i != trainingList.size()) {
            ApiException.setError(StatusCodeConstant.TRAINER_IMPORT_ERROR);
        }
    }

    @Override
    public Map<String, Object> getPersonnelList(Integer pageSize, Integer pageNumber) {
        PageHelper.startPage(pageNumber, pageSize);
        List<PersonnelList> personnelLists = baseMapper.queryPersonnelList();
        PageInfo<PersonnelList> pageInfo = new PageInfo<>(personnelLists);
        return new HashMap<>(3) {
            {
                put("total", pageInfo.getTotal());
                put("pageNumber", pageInfo.getPageNum());
                put("tableData", personnelLists);
            }
        };
    }

    @Override
    public void deleteUser(Integer uid) {
        if (baseMapper.deleteById(uid) != 1) {
            ApiException.setError(StatusCodeConstant.DELETE_USER_ERROR);
        }
    }

    @Override
    public void updateUserInfo(UpdateUserInfoReqFormAdmin userInfoForm) {
        if (baseMapper.updateById(new User(userInfoForm.uid()
                , userInfoForm.studentClass()
                , userInfoForm.sex()
                , userInfoForm.name()
                , userInfoForm.authority()
                , userInfoForm.state())) != 1) {
            ApiException.setError(0, "人员信息更新失败");
        }
    }

    /**
     * 更新做题总数
     *
     * @param uid            用户id
     * @param questionNumber 做题数
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/01/14
     */
    public Boolean updateQuestionNumber(Integer uid, Integer questionNumber) {
        if (baseMapper.updateQuestionNumber(uid, questionNumber) != 1) {
            log.warn("用户 {} 更新做题总数失败", uid);
            return false;
        }
        return true;
    }

    /**
     * 更新打卡状态
     *
     * @param uid          用户id
     * @param clockInState 打卡状态
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/02/05
     */
    public Boolean updateClockInState(Integer uid, Integer clockInState) {
        if (baseMapper.updateClockInState(uid, clockInState) != 1) {
            log.warn("用户 {} 更新打卡状态失败", uid);
            return false;
        }
        return true;
    }

    /**
     * 更新周报状态
     *
     * @param uid         用户id
     * @param weeklyState 周报状态
     * @return Boolean
     * @author Nuyoahz
     * @date 2023/12/05
     */
    public Boolean updateWeeklyState(Integer uid, Integer weeklyState) {
        if (baseMapper.updateWeeklyState(uid, weeklyState) != 1) {
            log.warn("用户 {} 更新周报状态失败", uid);
            return false;
        }
        return true;
    }

    /**
     * 获取未打卡人员(Redis)
     *
     * @return List<ClockInListAllF>
     * @author Nuyoahz
     * @date 2024/01/14
     */
    public List<ClockInListF> getClockInListRedis() {
        return baseMapper.queryClockInListF();
    }

    /**
     * 获取提交周报人员(Redis)
     *
     * @return List<WeeklyListF>
     * @author Nuyoahz
     * @date 2024/5/11
     */
    public List<WeeklyListF> getWeeklyListRedis() {
        return baseMapper.queryWeeklyListF();
    }
}