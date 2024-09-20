package com.scitc.nuyoahz.thunderstudiobackendspringboot.controller;

import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ResponseData;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.WeeklyDataForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.ClockInServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.WeeklyServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.AESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 普通接口
 *
 * @author Nuyoahz
 * @date 2024/1/10 15:54
 */
@RestController
@RequestMapping("/everyMan")
@RequiredArgsConstructor
@Log4j2
public class EveryManController {
    private final ClockInServiceImpl clockInService;
    private final WeeklyServiceImpl weeklyService;

    /**
     * 上传打卡记录接口
     *
     * @param request     请求
     * @param pictureFile 打卡文件
     * @param number      做题数
     * @param logs        日志
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/01/12 22:36
     */
    @PostMapping("/uploadClockIn")
    public Response uploadClockIn(HttpServletRequest request
            , @RequestPart("pictureFile") MultipartFile pictureFile
            , @RequestParam("number") Integer number
            , @RequestParam("logs") String logs) {
        if (pictureFile.isEmpty()) {
            ApiException.setError(StatusCodeConstant.EMPTY_FILE);
        }
        if (!SystemConstant.IMAGE_FORMAT.contains(pictureFile.getContentType())) {
            ApiException.setError(StatusCodeConstant.FILE_FORMAT_ERROR);
        }
        clockInService.uploadClockIn(CommonUtils.getRedisUserInfo(request).get(0), pictureFile, number, logs);
        return Response.info(StatusCodeConstant.CLOCK_IN_SUBMIT_SUCCESS);
    }

    /**
     * 更新打卡记录接口
     *
     * @param recordsId   打卡记录id
     * @param pictureFile 打卡文件
     * @param number      做题数
     * @param logs        日志
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/02/21 16:04
     */
    @PutMapping("/updateClockIn")
    public Response updateClockIn(@RequestParam("recordsId") Integer recordsId
            , @RequestPart("pictureFile") MultipartFile pictureFile
            , @RequestParam("number") Integer number
            , @RequestParam("logs") String logs) {
        if (pictureFile.isEmpty()) {
            ApiException.setError(StatusCodeConstant.EMPTY_FILE);
        }
        if (!SystemConstant.IMAGE_FORMAT.contains(pictureFile.getContentType())) {
            ApiException.setError(StatusCodeConstant.FILE_FORMAT_ERROR);
        }
        clockInService.updateClockIn(recordsId, pictureFile, number, logs);
        return Response.info(StatusCodeConstant.CLOCK_IN_UPDATE_SUCCESS);
    }

    /**
     * 删除打卡记录接口
     *
     * @param recordsId 打卡记录id
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/02/20 15:35
     */
    @DeleteMapping("/deleteClockIn")
    public Response deleteClockIn(@RequestParam("recordsId") Integer recordsId) {
        clockInService.deleteClockIn(recordsId);
        return Response.info(StatusCodeConstant.CLOCK_IN_DELETE_SUCCESS);
    }

    /**
     * 获取个人打卡记录接口
     *
     * @param request     请求
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ResponseData<java.lang.String>
     * @author Nuyoahz
     * @date 2024/03/18 17:00
     */
    @GetMapping("/getClockInList")
    public ResponseData<Map<String, Object>> getClockInList(HttpServletRequest request
            , @RequestParam("currentPage") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> clockInList = clockInService.getClockInList(CommonUtils.getRedisUserInfo(request).get(0), currentPage, pageSize);
        List<String> aesKey = CommonUtils.getAESKey(request);
        AESUtils aesUtils = new AESUtils(aesKey.get(0), aesKey.get(1));
        String jsonStr = JSONUtil.toJsonStr(clockInList.get("tableData"));
        clockInList.put("tableData", aesUtils.encrypt(jsonStr));
        return ResponseData.info(clockInList);
    }

    /**
     * 上传周报文件接口
     *
     * @param request    请求
     * @param weeklyFile 周报文件
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/02/20
     */
    @PostMapping("/uploadWeeklyFile")
    public Response uploadWeeklyFile(HttpServletRequest request, @RequestPart("weeklyFile") MultipartFile weeklyFile) {
        if (weeklyFile.isEmpty()) {
            ApiException.setError(StatusCodeConstant.EMPTY_FILE);
        }
        if (!SystemConstant.WORD_FORMAT.contains(weeklyFile.getContentType())) {
            ApiException.setError(StatusCodeConstant.FILE_FORMAT_ERROR);
        }
        weeklyService.uploadWeekly(CommonUtils.getRedisUserInfo(request).get(0), weeklyFile);
        return Response.info(StatusCodeConstant.WEEKLY_SUBMIT_SUCCESS);
    }

    /**
     * 上传周报内容接口
     *
     * @param request        请求
     * @param weeklyDataForm 周报表单
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/02/20
     */
    @PostMapping("/uploadWeeklyContent")
    public Response uploadWeeklyContent(HttpServletRequest request, @RequestBody WeeklyDataForm weeklyDataForm) {
        if (weeklyDataForm.weeklyTitle().isEmpty() || weeklyDataForm.weeklyContent().isEmpty()) {
            ApiException.setError(StatusCodeConstant.NULL_PARAMETER_ERROR);
        }
        weeklyService.uploadWeekly(CommonUtils.getRedisUserInfo(request).get(0), weeklyDataForm.weeklyTitle(), weeklyDataForm.weeklyContent());
        return Response.info(StatusCodeConstant.WEEKLY_SUBMIT_SUCCESS);
    }

    /**
     * 删除周报接口
     *
     * @param weeklyId 周报ID
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response
     * @author Nuyoahz
     * @date 2024/03/19
     */
    @DeleteMapping("/deleteWeekly")
    public Response deleteWeekly(@RequestParam("weeklyId") Integer weeklyId) {
        weeklyService.deleteWeekly(weeklyId);
        return Response.info(StatusCodeConstant.WEEKLY_DELETE_SUCCESS);
    }

    /**
     * 查询个人周报记录接口
     *
     * @param request     请求
     * @param currentPage 当前页码
     * @param pageSize    指定展示多少条
     * @return com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ResponseData<java.util.Map < java.lang.String, java.lang.Object>>
     * @author Nuyoahz
     * @date 2024/03/19
     */
    @GetMapping("/getWeeklyList")
    public ResponseData<Map<String, Object>> getWeeklyList(HttpServletRequest request
            , @RequestParam("currentPage") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> weeklyList = weeklyService.getWeeklyList(CommonUtils.getRedisUserInfo(request).get(0), currentPage, pageSize);
        List<String> aesKey = CommonUtils.getAESKey(request);
        AESUtils aesUtils = new AESUtils(aesKey.get(0), aesKey.get(1));
        String jsonStr = JSONUtil.toJsonStr(weeklyList.get("tableData"));
        weeklyList.put("tableData", aesUtils.encrypt(jsonStr));
        return ResponseData.info(weeklyList);
    }
}
