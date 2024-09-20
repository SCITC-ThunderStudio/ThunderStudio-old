package com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.word.Word07Writer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper.WeeklyMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.EvaluateWeeklyForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyRecord;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Weekly;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.WeeklyService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 针对表【weekly(周报记录表)】的数据库操作Service实现
 *
 * @author Nuyoahz
 * @date 2024/02/20
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class WeeklyServiceImpl extends ServiceImpl<WeeklyMapper, Weekly>
        implements WeeklyService {
    private final UserServiceImpl userService;

    @Value("${host.url}")
    public String host;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadWeekly(Integer uid, MultipartFile weeklyFile) {
        // 更新周报提交状态
        if (!userService.updateWeeklyState(uid, 1)) {
            log.error("用户 {} 更新周报提交状态失败", uid);
            ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
        }
        // 当天日期
        String date = CommonUtils.getTimeFormat(SystemConstant.YMD);
        // 周报文件文件绝对路径 jar包运行路径 + "WeeklyDocuments/" + 当天日期
        File absolutePath = new File(CommonUtils.getJarAbsolutePath() + SystemConstant.WEEKLY_DOCUMENTS_DIR + date);
        // 新文件名文件前缀
        String prefix = IdUtil.randomUUID().toUpperCase();
        // 文件后缀
        String suffix = Objects.requireNonNull(weeklyFile.getOriginalFilename()).substring(weeklyFile.getOriginalFilename().lastIndexOf("."));
        // 旧文件名
        String oldFileName = weeklyFile.getOriginalFilename();
        // 新文件名
        String newFileName = prefix + suffix;
        // 准备文件放入文件夹中
        File newFile = new File(absolutePath, newFileName);
        // 周报文件文件相对路径 当天日期 + 文件名
        String relativePath = date + "/" + newFileName;
        // 添加周报记录
        if (baseMapper.insert(new Weekly(uid, oldFileName, relativePath)) != 1) {
            log.error("用户 {} 周报记录添加失败", uid);
            ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
        }
        // 文件夹不存在则创建
        if (!absolutePath.exists()) {
            if (!absolutePath.mkdirs()) {
                log.error("周报文件夹创建失败");
                ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
            }
            log.info("周报文件夹创建成功,周报目录路径:{}", absolutePath);
        }
        try {
            // 将周报文件放入文件夹
            weeklyFile.transferTo(newFile);
            log.info("用户 {} 周报文件相对路径:{}", uid, relativePath);
        } catch (IOException e) {
            log.error("用户 {} 周报文件持久化失败:{}", uid, e.getMessage());
            ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadWeekly(Integer uid, String weeklyTitle, String weeklyContent) {
        try (Word07Writer writer = new Word07Writer()) {
            // word 文件名
            String fileName = IdUtil.randomUUID().toUpperCase();
            // word 文档标题
            writer.addText(ParagraphAlignment.CENTER, new Font("黑体", Font.PLAIN, 28), weeklyTitle);
            // word 文档正文
            writer.addText(ParagraphAlignment.BOTH, new Font("宋体", Font.PLAIN, 22), weeklyContent);
            // 当天日期
            String date = CommonUtils.getTimeFormat(SystemConstant.YMD);
            // 周报文件绝对路径 jar包运行路径 + "WeeklyDocuments/" + 当天日期 + 文件名
            String absolutePath = CommonUtils.getJarAbsolutePath() + SystemConstant.WEEKLY_DOCUMENTS_DIR + date + '/' + fileName + ".docx";
            // 周报文件文件相对路径 当天日期 + 文件名
            String relativePath = date + '/' + fileName + ".docx";
            if (baseMapper.insert(new Weekly(uid, weeklyTitle + ".docx", relativePath)) <= 0) {
                ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
            }
            // 写出到文件
            writer.flush(FileUtil.file(absolutePath));
            log.info("用户 {} 周报文件相对路径:{}", uid, relativePath);
        } catch (Exception e) {
            log.error("用户 {} 周报持久化失败:{}", uid, e.getMessage());
            ApiException.setError(StatusCodeConstant.WEEKLY_SUBMIT_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWeekly(Integer weeklyId) {
        Weekly weekly = baseMapper.selectById(weeklyId);
        if (weekly == null) {
            log.error("周报记录 {} 不存在，", weeklyId);
            ApiException.setError(StatusCodeConstant.WEEKLY_NOT_FOUND_ERROR);
        }
        Integer uid = weekly.getUid();
        String relativePath = weekly.getFilePath();
        if (baseMapper.deleteById(weeklyId) == 0) {
            log.error("用户 {} 周报记录删除失败,周报记录ID:{}", uid, weeklyId);
            ApiException.setError(StatusCodeConstant.WEEKLY_DELETE_ERROR);
        }
        // 查询本周学生周报记录数
        if (baseMapper.queryWeeklyNumber(uid) <= 0) {
            userService.updateWeeklyState(uid, 0);
        }
        // 周报文件绝对路径
        String absolutePath = CommonUtils.getJarAbsolutePath() + SystemConstant.WEEKLY_DOCUMENTS_DIR + relativePath;
        if (!new File(absolutePath).delete()) {
            log.error("用户 {} 周报文件删除失败,周报记录ID:{},周报路径:{}", uid, absolutePath, weeklyId);
            ApiException.setError(StatusCodeConstant.WEEKLY_DELETE_ERROR);
        }
    }

    @Override
    public Map<String, Object> getWeeklyList(Integer uid, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        // 链接前缀
        String urlPrefix = host + '/' + SystemConstant.WEEKLY_NETWORK_PATH;
        List<WeeklyList> weeklyList = baseMapper.queryWeeklyList(uid, urlPrefix);
        PageInfo<WeeklyList> pageInfo = new PageInfo<>(weeklyList);
        return new HashMap<>(4) {
            {
                put("total", pageInfo.getTotal());
                put("pageSize", pageInfo.getSize());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", weeklyList);
            }
        };
    }

    @Override
    public Map<String, Object> getWeeklyRecord(Integer option, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        String urlPrefix = host + '/' + SystemConstant.WEEKLY_NETWORK_PATH;
        List<WeeklyRecord> weeklyRecordList = option == 0 ? baseMapper.queryWeeklyRecord(urlPrefix) : baseMapper.queryWeeklyRecordAll(urlPrefix);
        PageInfo<WeeklyRecord> pageInfo = new PageInfo<>(weeklyRecordList);
        return new HashMap<>(4) {
            {
                put("total", pageInfo.getTotal());
                put("pageSize", pageInfo.getSize());
                put("currentPage", pageInfo.getPageNum());
                put("tableData", weeklyRecordList);
            }
        };
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void evaluateWeekly(EvaluateWeeklyForm evaluateWeeklyForm) {
        if (baseMapper.updateEvaluate(evaluateWeeklyForm.weeklyId(), evaluateWeeklyForm.evaluateContent()) != 1) {
            ApiException.setError(StatusCodeConstant.WEEKLY_COMMENTS_ERROR);
        }
    }
}