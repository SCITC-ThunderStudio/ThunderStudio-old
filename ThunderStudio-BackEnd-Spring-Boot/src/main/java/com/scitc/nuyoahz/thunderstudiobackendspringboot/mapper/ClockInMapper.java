package com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ClockInRecord;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.ClockIn;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 针对表【clock_in(打卡记录表)】的数据库操作Mapper
 *
 * @author Nuyoahz
 * @date 2024/02/05
 */
public interface ClockInMapper extends BaseMapper<ClockIn> {
    /**
     * 查询个人打卡记录
     *
     * @param uid  用户id
     * @param urlPrefix 网络前缀
     * @return List<ClockInList>
     * @author Nuyoahz
     * @date 2024/03/18
     */
    List<ClockInList> queryClockInList(@Param("uid") Integer uid, @Param("urlPrefix") String urlPrefix);

    /**
     * 查询今日所有打卡记录
     *
     * @param urlPrefix 网络前缀
     * @return List<ClockInRecord>
     * @author Nuyoahz
     * @date 2024/02/06
     */
    List<ClockInRecord> queryClockInRecord(@Param("urlPrefix") String urlPrefix);

    /**
     * 查询所有打卡记录
     *
     * @param urlPrefix 网络前缀
     * @return List<ClockInRecord>
     * @author Nuyoahz
     * @date 2024/02/06
     */
    List<ClockInRecord> queryClockInRecordAll(@Param("urlPrefix") String urlPrefix);

    /**
     * 更新打卡记录
     *
     * @param clockIn 打卡记录实体
     * @return java.lang.Integer
     * @author Nuyoahz
     * @date 2024/02/22
     */
    Integer updateClockIn(ClockIn clockIn);

    /**
     * 查询今天指定uid记录总数
     *
     * @param uid 用户id
     * @return java.lang.Integer
     * @author Nuyoahz
     * @date 2024/04/16
     */
    Integer queryTotalToday(@Param("uid") Integer uid);

    /**
     * 查询题量分布
     *
     * @return List<Map < Integer>>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    @MapKey(value = "number")
    List<Map<String, Integer>> queryQuestionQuantityDistribution();
}