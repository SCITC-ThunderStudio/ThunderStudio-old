package com.scitc.nuyoahz.thunderstudiobackendspringboot.controller;

import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.EvaluateWeeklyForm;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.Response;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.ResponseData;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.UpdateUserInfoReqFormAdmin;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.ClockInServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.UserServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.WeeklyServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.AESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.VerifyUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理员接口
 *
 * @author Nuyoahz
 * @date 2024/1/12
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminController {
    private final UserServiceImpl userService;
    private final ClockInServiceImpl clockInService;
    private final WeeklyServiceImpl weeklyService;

    /**
     * 获取未打卡人员接口
     *
     * @param request 请求
     * @param option  选项,0今日未打卡人员,1昨日未打卡人员
     * @return ResponseData<String>
     * @author Nuyoahz
     * @date 2024/01/14
     */
    @GetMapping("/getClockInListF")
    public ResponseData<String> getClockInListF(HttpServletRequest request, @RequestParam(value = "option", defaultValue = "0") Integer option) {
        String jsonStr = JSONUtil.toJsonStr(userService.getClockInListF(option)).trim();
        List<String> aesKey = CommonUtils.getAESKey(request);
        return ResponseData.info(new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(jsonStr));
    }

    /**
     * 获取题量分布接口
     *
     * @return ResponseData<Map < ArrayList < Object>>>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    @GetMapping("/getQuestionQuantityDistribution")
    public ResponseData<Map<String, ArrayList<Object>>> getQuestionQuantityDistribution() {
        return ResponseData.info(clockInService.getQuestionQuantityDistribution());
    }

    /**
     * 获取打卡记录接口
     *
     * @param request     请求
     * @param option      选项 0今日,1全部
     * @param currentPage 当前页码
     * @param pageSize    每页记录数,默认每页十条
     * @return ResponseData<Map < Object>>
     * @author Nuyoahz
     * @date 2023/12/10
     */
    @GetMapping("/getClockInRecord")
    public ResponseData<Map<String, Object>> getClockInRecord(HttpServletRequest request
            , @RequestParam(value = "option", defaultValue = "0") Integer option
            , @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> clockInRecord = clockInService.getClockInRecord(option, currentPage, pageSize);
        String tableData = JSONUtil.toJsonStr(clockInRecord.get("tableData")).trim();
        List<String> aesKey = CommonUtils.getAESKey(request);
        String encrypt = new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(tableData);
        clockInRecord.put("tableData", encrypt);
        return ResponseData.info(clockInRecord);
    }

    /**
     * 获取未提交周报人员名单接口
     *
     * @param request 请求
     * @param option  选项 0本周,1上周
     * @return ResponseData<String>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    @GetMapping("/getWeeklyListF")
    public ResponseData<String> getWeeklyListF(HttpServletRequest request, @RequestParam(value = "option", defaultValue = "0") Integer option) {
        String jsonStr = JSONUtil.toJsonStr(userService.getWeeklyListF(option)).trim();
        List<String> aesKey = CommonUtils.getAESKey(request);
        String encrypt = new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(jsonStr);
        return ResponseData.info(encrypt);
    }

    /**
     * 获取周报记录接口
     *
     * @param request     请求
     * @param option      选项 0本周,1全部
     * @param currentPage 当前页码
     * @param pageSize    每页记录数,默认每页十条
     * @return ResponseData<Map < Object>>
     * @author Nuyoahz
     * @date 2024/5/9
     */
    @GetMapping("/getWeeklyRecord")
    public ResponseData<Map<String, Object>> getWeeklyRecord(HttpServletRequest request
            , @RequestParam(value = "option", defaultValue = "0") Integer option
            , @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Map<String, Object> weeklyRecord = weeklyService.getWeeklyRecord(option, currentPage, pageSize);
        String tableData = JSONUtil.toJsonStr(weeklyRecord.get("tableData")).trim();
        List<String> aesKey = CommonUtils.getAESKey(request);
        String encrypt = new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(tableData);
        weeklyRecord.put("tableData", encrypt);
        return ResponseData.info(weeklyRecord);
    }

    /**
     * 评价周报接口
     *
     * @param evaluateWeeklyForm 评价周报表单
     * @return Response
     * @author Nuyoahz
     * @date 2024/5/11
     */
    @PatchMapping("/evaluateWeekly")
    public Response evaluateWeekly(@RequestBody EvaluateWeeklyForm evaluateWeeklyForm) {
        weeklyService.evaluateWeekly(evaluateWeeklyForm);
        return Response.info(StatusCodeConstant.WEEKLY_COMMENTS_SUCCESS);
    }

    /**
     * 上传训练营名单接口
     *
     * @param trainingListFile 训练营名单文件
     * @return Response
     * @author Nuyoahz
     * @date 2024/5/11
     */
    @PostMapping("uploadTrainingListFile")
    public Response uploadTrainingList(@RequestPart("TrainingList") MultipartFile trainingListFile) {
        if (trainingListFile.isEmpty()) {
            ApiException.setError(StatusCodeConstant.EMPTY_FILE);
        }
        if (!VerifyUtils.isExcel(trainingListFile.getContentType())) {
            ApiException.setError(StatusCodeConstant.FILE_FORMAT_ERROR);
        }
        userService.uploadTrainingList(trainingListFile);
        return Response.info(StatusCodeConstant.TRAINER_IMPORT_SUCCESS);
    }

    /**
     * 下载训练营名单模版接口
     *
     * @param response 响应
     * @author Nuyoahz
     * @date 2024/5/14
     */
    @GetMapping("/downloadTrainingListFile")
    public void downloadTrainingListFile(HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("download/雷霆算法训练营名单.xlsx");
            response.setContentType("application/octet-stream");
            // 待下载文件名
            String fileName = URLEncoder.encode("雷霆算法训练营名单.xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            outputStream = response.getOutputStream();
            if (inputStream != null) {
                byte[] results = FileCopyUtils.copyToByteArray(inputStream);
                outputStream.write(results);
                outputStream.flush();
            }
        } catch (IOException e) {
            ApiException.setError(StatusCodeConstant.SYSTEM_ERROR);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 获取人员名单接口
     *
     * @param request    请求
     * @param pageSize   每页记录数
     * @param pageNumber 页码
     * @return ResponseData<Map < Object>>
     * @author Nuyoahz
     * @date 2024/5/12
     */
    @GetMapping("/getPersonnelList")
    public ResponseData<Map<String, Object>> getPersonnelList(HttpServletRequest request
            , @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
            , @RequestParam(value = "currentPage", defaultValue = "1") Integer pageNumber) {
        Map<String, Object> personnelList = userService.getPersonnelList(pageSize, pageNumber);
        String tableData = JSONUtil.toJsonStr(personnelList.get("tableData")).trim();
        List<String> aesKey = CommonUtils.getAESKey(request);
        String encrypt = new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(tableData);
        personnelList.put("tableData", encrypt);
        return ResponseData.info(personnelList);
    }

    @DeleteMapping("/deleteUser")
    public Response deleteUser(HttpServletRequest request, @RequestParam("uid") Integer uid) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        if (uid == 20163021) {
            ApiException.setError(0, "禁止删除此账号");
        }
        userService.deleteUser(uid);
        return Response.info(StatusCodeConstant.SUCCESS);
    }

    @PutMapping("/updateUserInfo")
    public Response updateUserInfo(HttpServletRequest request, @RequestBody UpdateUserInfoReqFormAdmin userInfoForm) {
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            ApiException.setError(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        userService.updateUserInfo(userInfoForm);
        return Response.info(StatusCodeConstant.SUCCESS);
    }
}
