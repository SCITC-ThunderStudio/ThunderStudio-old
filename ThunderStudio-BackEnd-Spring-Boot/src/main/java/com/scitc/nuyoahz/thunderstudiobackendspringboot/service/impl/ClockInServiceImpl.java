package com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper.ClockInMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInRecord;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.ClockIn;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.ClockInService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 针对表【clock_in(打卡记录表)】的数据库操作Service实现
 *
 * @author Nuyoahz
 * @date 2024/02/05 20:21:29
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ClockInServiceImpl extends ServiceImpl<ClockInMapper, ClockIn>
        implements ClockInService {
    private final UserServiceImpl userService;

    @Value("${host.url}")
    private String host;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadClockIn(Integer uid, MultipartFile pictureFile, Integer number, String logs) {
        // 更新做题总数和打卡状态
        if (!userService.updateQuestionNumber(uid, number) || !userService.updateClockInState(uid, 1)) {
            ApiException.setError(StatusCodeConstant.CLOCK_IN_SUBMIT_ERROR);
        }
        // 当天日期
        String date = CommonUtils.getTimeFormat(SystemConstant.YMD);
        // 打卡记录截图文件绝对路径 jar包运行路径 + "PunchInPicture/" + 当天日期
        File absolutePath = new File(CommonUtils.getJarAbsolutePath() + SystemConstant.PUNCH_IN_PICTURE_DIR + date);
        // 文件前缀
        String prefix = IdUtil.randomUUID().toUpperCase();
        // 文件后缀
        String suffix = Objects.requireNonNull(pictureFile.getOriginalFilename()).substring(pictureFile.getOriginalFilename().lastIndexOf("."));
        // 新文件名
        String newFileName = prefix + suffix;
        // 准备文件放入文件夹中
        File newFile = new File(absolutePath, newFileName);
        // 文件相对路径
        String relativePath = date + "/" + newFileName;
        // 打卡记录持久化
        if (baseMapper.insert(new ClockIn(uid, number, logs, relativePath)) != 1) {
            log.warn("打卡记录写入失败");
            ApiException.setError(StatusCodeConstant.CLOCK_IN_SUBMIT_ERROR);
        }
        // 文件夹不存在则创建
        if (!absolutePath.exists()) {
            if (!absolutePath.mkdirs()) {
                log.error("文件夹创建失败");
                ApiException.setError(StatusCodeConstant.CLOCK_IN_SUBMIT_ERROR);
            }
            log.info("文件夹创建成功,目录路径:{}", absolutePath);
        }
        try {
            // 将文件放入文件夹
            pictureFile.transferTo(newFile);
            log.info("打卡图片文件相对路径:{}", relativePath);
        } catch (IOException e) {
            log.error("打卡图片持久化失败 {}", e.getMessage());
            ApiException.setError(StatusCodeConstant.CLOCK_IN_SUBMIT_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClockIn(Integer recordsId, MultipartFile pictureFile, Integer number, String logs) {
        // 获取指定打卡记录ID
        ClockIn clockIn = baseMapper.selectById(recordsId);
        // 获取jar包运行路径
        String jarPath = CommonUtils.getJarAbsolutePath();
        // 旧文件文件的绝对路径
        String oldAbsolutePath = jarPath + SystemConstant.PUNCH_IN_PICTURE_DIR + clockIn.getPicturePath();
        // 获取旧文件的相对路径，不含文件名
        int indexEnd = clockIn.getPicturePath().lastIndexOf('/');
        String oldRelativePath = clockIn.getPicturePath().substring(0, indexEnd + 1);
        // 文件绝对路径
        File absolutePath = new File(CommonUtils.getJarAbsolutePath() + SystemConstant.PUNCH_IN_PICTURE_DIR + oldRelativePath);
        // 文件前缀
        String prefix = IdUtil.randomUUID().toUpperCase();
        // 文件后缀
        String suffix = Objects.requireNonNull(pictureFile.getOriginalFilename()).substring(pictureFile.getOriginalFilename().lastIndexOf("."));
        // 新文件名
        String newFileName = prefix + suffix;
        // 文件相对路径，包含文件名
        String relativePath = oldRelativePath + newFileName;
        // 相比之前做题数多还是少
        Integer newNumber = number - clockIn.getNumber();
        // 更新做题总数
        if (!userService.updateQuestionNumber(clockIn.getUid(), newNumber)) {
            ApiException.setError(StatusCodeConstant.CLOCK_IN_SUBMIT_ERROR);
        }
        // 打卡记录更新
        if (baseMapper.updateClockIn(new ClockIn(recordsId, number, CommonUtils.getTimeFormat(SystemConstant.YMDHMS), logs, relativePath)) != 1) {
            log.error("打卡记录更新失败");
            ApiException.setError(StatusCodeConstant.CLOCK_IN_UPDATE_ERROR);
        }
        // 准备新文件放入文件夹中
        File newFile = new File(absolutePath, newFileName);
        try {
            // 将文件放入文件夹
            pictureFile.transferTo(newFile);
            log.info("文件相对路径:{}", relativePath);
        } catch (IOException e) {
            log.error("打卡图片持久化失败 {}", e.getMessage());
            ApiException.setError(StatusCodeConstant.FILE_WRITE_ERROR);
        }
        // 删除旧文件
        if (!new File(oldAbsolutePath).delete()) {
            newFile.delete();
            log.error("打卡图片旧文件删除失败");
            ApiException.setError(StatusCodeConstant.CLOCK_IN_DELETE_ERROR);
        }
    }

    @Override
    public Map<String, Object> getClockInList(Integer uid, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        // 链接前缀
        String urlPrefix = host + '/' + SystemConstant.IMG_NETWORK_PATH;
        List<ClockInList> clockInLists = baseMapper.queryClockInList(uid, urlPrefix);
        PageInfo<ClockInList> pageInfo = new PageInfo<>(clockInLists);
        return new HashMap<>(4) {
            {
                put("total", pageInfo.getTotal());
                put("pageSize", pageInfo.getSize());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", clockInLists);
            }
        };
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClockIn(Integer recordsId) {
        ClockIn clockIn = baseMapper.selectById(recordsId);
        String picturePath = clockIn.getPicturePath();
        Integer uid = clockIn.getUid();
        Integer number = clockIn.getNumber();
        if (baseMapper.deleteById(recordsId) != 1) {
            ApiException.setError(StatusCodeConstant.CLOCK_IN_DELETE_ERROR);
        }
        boolean isToday = String.format("%tF", clockIn.getSubmitTime()).equals(CommonUtils.getTimeFormat(SystemConstant.YMD));
        if (isToday) {
            if (baseMapper.queryTotalToday(uid) < 1) {
                if (!userService.updateClockInState(uid, 0)) {
                    ApiException.setError(StatusCodeConstant.CLOCK_IN_UPDATE_ERROR);
                }
            }
        }
        // 获取打卡文件绝对路径
        String absolutePath = CommonUtils.getJarAbsolutePath() + SystemConstant.PUNCH_IN_PICTURE_DIR + picturePath;
        if (!userService.updateQuestionNumber(uid, -number) || !new File(absolutePath).delete()) {
            ApiException.setError(StatusCodeConstant.CLOCK_IN_DELETE_ERROR);
        }
    }

    @Override
    public Map<String, ArrayList<Object>> getQuestionQuantityDistribution() {
        List<Map<String, Integer>> list = baseMapper.queryQuestionQuantityDistribution();
        ArrayList<Object> numberList = new ArrayList<>();
        ArrayList<Object> totalRecordsList = new ArrayList<>();
        list.forEach(map -> {
            numberList.add(map.get("number"));
            totalRecordsList.add(map.get("totalRecords"));
        });
        return new HashMap<>(2) {
            {
                put("number", numberList);
                put("totalRecords", totalRecordsList);
            }
        };
    }

    @Override
    public Map<String, Object> getClockInRecord(Integer option, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        // 链接前缀
        String urlPrefix = host + '/' + SystemConstant.IMG_NETWORK_PATH;
        List<ClockInRecord> clockInRecordList = option == 0 ? baseMapper.queryClockInRecord(urlPrefix) : baseMapper.queryClockInRecordAll(urlPrefix);
        PageInfo<ClockInRecord> pageInfo = new PageInfo<>(clockInRecordList);
        return new HashMap<>(4) {
            {
                put("total", pageInfo.getTotal());
                put("pageSize", pageInfo.getSize());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", clockInRecordList);
            }
        };
    }
}
