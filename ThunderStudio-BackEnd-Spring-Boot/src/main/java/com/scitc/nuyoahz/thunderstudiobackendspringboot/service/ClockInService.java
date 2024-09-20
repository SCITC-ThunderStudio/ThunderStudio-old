package com.scitc.nuyoahz.thunderstudiobackendspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.ClockIn;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 针对表【clock_in(打卡记录表)】的数据库操作Service
 *
 * @author Nuyoahz
 * @date 2024/02/05
 */
public interface ClockInService extends IService<ClockIn> {
    /**
     * 上传打卡记录
     *
     * @param uid         用户id
     * @param pictureFile 打卡文件
     * @param number      做题数
     * @param logs        日志
     * @author Nuyoahz
     * @date 2024/02/20 15:32
     */
    void uploadClockIn(Integer uid, MultipartFile pictureFile, Integer number, String logs);

    /**
     * 更新打卡记录
     *
     * @param recordsId   打卡记录id
     * @param pictureFile 打卡文件
     * @param number      做题数
     * @param logs        日志
     * @author Nuyoahz
     * @date 2024/03/18 17:52
     */
    void updateClockIn(Integer recordsId, MultipartFile pictureFile, Integer number, String logs);

    /**
     * 获取个人打卡记录
     *
     * @param uid         用户id
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Nuyoahz
     * @date 2024/04/14 07:19
     */
    Map<String, Object> getClockInList(Integer uid, Integer currentPage, Integer pageSize);

    /**
     * 删除打卡记录
     *
     * @param recordsId 打卡记录id
     * @author Nuyoahz
     * @date 2024/02/20 15:39
     */
    void deleteClockIn(Integer recordsId);

    /**
     * 获取题量分布
     *
     * @return ResponseData<Map < ArrayList < Object>>>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    Map<String, ArrayList<Object>> getQuestionQuantityDistribution();

    /**
     * 查询打卡记录
     *
     * @param option      选项 0当天,1全部
     * @param currentPage 当前页码
     * @param pageSize    每页记录数,默认每页十条
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Nuyoahz
     * @date 2024/02/20 15:33
     */
    Map<String, Object> getClockInRecord(Integer option, Integer currentPage, Integer pageSize);
}
