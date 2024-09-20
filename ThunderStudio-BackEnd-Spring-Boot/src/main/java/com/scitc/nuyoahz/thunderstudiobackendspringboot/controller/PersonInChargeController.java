package com.scitc.nuyoahz.thunderstudiobackendspringboot.controller;

import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.AnnouncementServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.UserServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.AESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 负责人接口
 *
 * @author Nuyoahz
 * @date 2024/1/12
 */
@RestController
@RequestMapping("/personInCharge")
@RequiredArgsConstructor
@Log4j2
public class PersonInChargeController {
    private final UserServiceImpl userService;
    private final AnnouncementServiceImpl announcementService;

    /**
     * 获取个人负责人员接口
     *
     * @param request     请求
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return ResponseData<Map < Object>>
     * @author Nuyoahz
     * @date 2024/4/18
     */
    @GetMapping("/getTrainee")
    public ResponseData<Map<String, Object>> getTrainee(HttpServletRequest request
            , @RequestParam("currentPage") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        Map<String, Object> traineeMap = userService.getTrainee(redisUserInfo.get(0), currentPage, pageSize);
        List<String> aesKey = CommonUtils.getAESKey(request);
        AESUtils aesUtils = new AESUtils(aesKey.get(0), aesKey.get(1));
        String jsonStr = JSONUtil.toJsonStr(traineeMap.get("tableData"));
        traineeMap.put("tableData", aesUtils.encrypt(jsonStr));
        return ResponseData.info(traineeMap);
    }

    /**
     * 获取全部公告
     *
     * @param request     请求
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return Map<Object>
     */
    @GetMapping("/getAnnouncementAllList")
    public ResponseData<Map<String, Object>> getAnnouncementAllList(HttpServletRequest request
            , @RequestParam("currentPage") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        return ResponseData.info(announcementService.getAnnouncementList(currentPage, pageSize));
    }

    /**
     * 发布公告接口
     *
     * @param request          请求
     * @param announcementForm 公告表单
     * @return Response
     * @author Nuyoahz
     * @date 2024/4/22
     */
    @PostMapping("/announcement")
    public Response announcement(HttpServletRequest request, @RequestBody AnnouncementForm announcementForm) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        announcementService.announcement(redisUserInfo.get(0), announcementForm);
        return Response.info(StatusCodeConstant.ANNOUNCEMENT_SUCCESS);
    }

    /**
     * 更新公告接口
     *
     * @param request                请求
     * @param announcementUpdateForm 公告更新表单
     * @return Response
     * @author Nuyoahz
     * @date 2024/4/22
     */
    @PutMapping("/updateAnnouncement")
    public Response updateAnnouncement(HttpServletRequest request, @RequestBody AnnouncementUpdateForm announcementUpdateForm) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        announcementService.updateAnnouncement(announcementUpdateForm);
        return Response.info(StatusCodeConstant.UPDATE_ANNOUNCEMENT_SUCCESS);
    }

    /**
     * 删除公告接口
     *
     * @param request        请求
     * @param announcementId 公告id
     * @return Response
     * @author Nuyoahz
     * @date 2024/4/22
     */
    @DeleteMapping("/deleteAnnouncement")
    public Response deleteAnnouncement(HttpServletRequest request, @RequestParam("announcementId") Integer announcementId) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        announcementService.deleteAnnouncement(announcementId);
        return Response.info(StatusCodeConstant.DELETE_ANNOUNCEMENT_SUCCESS);
    }
}
