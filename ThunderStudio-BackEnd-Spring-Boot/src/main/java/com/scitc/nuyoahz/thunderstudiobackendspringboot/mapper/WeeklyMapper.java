package com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyList;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyListF;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyRecord;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.entity.Weekly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 针对表【weekly(周报记录表)】的数据库操作Mapper
 *
 * @author Nuyoahz
 * @date 2024/02/20
 */
public interface WeeklyMapper extends BaseMapper<Weekly> {
    /**
     * 查询个人周报记录
     *
     * @param uid  用户id
     * @param urlPrefix 链接前缀
     * @return List<WeeklyList>
     * @author Nuyoahz
     * @date 2024/03/19
     */
    List<WeeklyList> queryWeeklyList(@Param("uid") Integer uid, @Param("urlPrefix") String urlPrefix);

    /**
     * 查询本周周报记录
     *
     * @param urlPrefix 链接前缀
     * @return List<WeeklyRecord>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    List<WeeklyRecord> queryWeeklyRecord(@Param("urlPrefix") String urlPrefix);

    /**
     * 查询所有周报记录
     *
     * @param urlPrefix 链接前缀
     * @return List<WeeklyRecord>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    List<WeeklyRecord> queryWeeklyRecordAll(@Param("urlPrefix") String urlPrefix);

    /**
     * 查询用户当周的周报记录数
     *
     * @param uid 用户id
     * @return Integer
     * @author Nuyoahz
     * @date 2024/03/19
     */
    Integer queryWeeklyNumber(@Param("uid") Integer uid);

    /**
     * 更新周报评价
     *
     * @param weeklyId        周报记录id
     * @param evaluateContent 评价内容
     * @return Integer
     * @author Nuyoahz
     * @date 2024/5/11
     */
    Integer updateEvaluate(@Param("weeklyId") Integer weeklyId, @Param("evaluateContent") String evaluateContent);
}