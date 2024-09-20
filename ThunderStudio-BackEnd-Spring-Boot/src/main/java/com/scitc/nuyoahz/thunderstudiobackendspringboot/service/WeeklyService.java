package com.scitc.nuyoahz.thunderstudiobackendspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.EvaluateWeeklyForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Weekly;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 针对表【weekly(周报记录表)】的数据库操作Service
 *
 * @author Nuyoahz
 * @date 2024/02/20
 */
public interface WeeklyService extends IService<Weekly> {
    /**
     * 上传周报文件
     *
     * @param uid        用户id
     * @param weeklyFile 周报文件
     * @author Nuyoahz
     * @date 2024/02/20
     */
    void uploadWeekly(Integer uid, MultipartFile weeklyFile);

    /**
     * 上传周报内容
     *
     * @param uid           用户id
     * @param weeklyTitle   周报标题
     * @param weeklyContent 周报内容
     * @author Nuyoahz
     * @date 2024/02/20
     */
    void uploadWeekly(Integer uid, String weeklyTitle, String weeklyContent);

    /**
     * 删除周报
     *
     * @param weeklyId 周报id
     * @author Nuyoahz
     * @date 2024/03/19
     */
    void deleteWeekly(Integer weeklyId);

    /**
     * 查询个人周报记录
     *
     * @param uid         用户id
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Nuyoahz
     * @date 2024/03/19
     */
    Map<String, Object> getWeeklyList(Integer uid, Integer currentPage, Integer pageSize);

    /**
     * 获取周报记录
     *
     * @param option      选项 0今日,1全部
     * @param currentPage 当前页码
     * @param pageSize    每页记录数,默认每页十条
     * @return Map<Object>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    Map<String, Object> getWeeklyRecord(Integer option, Integer currentPage, Integer pageSize);

    /**
     * 评价周报
     *
     * @param evaluateWeeklyForm 评价周报表单
     * @author Nuyoahz
     * @date 2024/5/11
     */
    void evaluateWeekly(EvaluateWeeklyForm evaluateWeeklyForm);
}
