package com.scitc.nuyoahz.thunderstudiobackendspringboot.controller;

import cn.hutool.json.JSONUtil;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.UserServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.AESUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.VerifyUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 用户接口
 *
 * @author Nuyoahz
 * @date 2024/1/14
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserServiceImpl userService;

    /**
     * 获取用户信息接口
     *
     * @param request 请求
     * @return ResponseData<String>
     * @author Nuyoahz
     * @date 2024/03/13 17:58
     */
    @GetMapping("/getUserInfo")
    public ResponseData<String> getUserInfo(HttpServletRequest request) {
        String jsonStr = JSONUtil.toJsonStr(userService.getUserInfo(CommonUtils.getRedisUserInfo(request).get(0)));
        List<String> aesKey = CommonUtils.getAESKey(request);
        return ResponseData.info(StatusCodeConstant.USER_INFO_SUCCESS, new AESUtils(aesKey.get(0), aesKey.get(1)).encrypt(jsonStr));
    }

    /**
     * 获取路由接口
     *
     * @param request 请求
     * @return ResponseData<List < RouterList>>
     * @author Nuyoahz
     * @date 2024/01/11
     */
    @GetMapping("/getRouter")
    public ResponseData<List<RouterList>> getRouter(HttpServletRequest request) {
        return ResponseData.info(userService.getRouter(CommonUtils.getRedisUserInfo(request).get(1)));
    }

    /**
     * 修改密码接口（登录）
     *
     * @param updatePasswordTForm 更新密码表单
     * @param request             请求
     * @return Response
     * @author Nuyoahz
     * @date 2024/02/05
     */
    @PutMapping("/updatePasswordT")
    public Response updatePasswordT(@RequestBody UpdatePasswordTForm updatePasswordTForm, HttpServletRequest request) {
        if (!VerifyUtils.isPassword(updatePasswordTForm.newPassword())) {
            ApiException.setError(StatusCodeConstant.PASSWORD_FORMAT_ERROR);
        }
        userService.updatePassword(updatePasswordTForm, CommonUtils.getRedisUserInfo(request).get(0));
        return Response.info(StatusCodeConstant.PASSWORD_UPDATE_SUCCESS);
    }

    /**
     * 更新头像接口
     *
     * @param request    请求
     * @param avatarFile 头像文件
     * @return Response
     * @author Nuyoahz
     * @date 2024/03/18
     */
    @PutMapping("/updateAvatar")
    public Response updateAvatar(HttpServletRequest request, @RequestPart("avatarFile") MultipartFile avatarFile) {
        if (avatarFile.isEmpty()) {
            ApiException.setError(StatusCodeConstant.EMPTY_FILE);
        }
        if (!VerifyUtils.isImg(avatarFile.getContentType())) {
            ApiException.setError(StatusCodeConstant.FILE_FORMAT_ERROR);
        }
        userService.updateAvatar(CommonUtils.getRedisUserInfo(request).get(0), avatarFile);
        return Response.info(StatusCodeConstant.UPDATE_AVATAR_SUCCESS);
    }

    /**
     * 更新人员状态接口
     *
     * @param request            请求
     * @param personnelStateForm 人员状态表单
     * @return Response
     * @author Nuyoahz
     * @date 2024/4/19
     */
    @PutMapping("/updatePersonnelState")
    public Response updatePersonnelState(HttpServletRequest request, @RequestBody PersonnelStateForm personnelStateForm) {
        if (Objects.equals(personnelStateForm.uid(), SystemConstant.ADMIN_UID)) {
            ApiException.setError(StatusCodeConstant.STATE_UPDATE_ROOT_ERROR);
        }
        List<Integer> redisUserInfo = CommonUtils.getRedisUserInfo(request);
        if (redisUserInfo.get(1) == 2) {
            return Response.info(StatusCodeConstant.INSUFFICIENT_PERMISSIONS);
        }
        userService.updateResponseState(personnelStateForm);
        return Response.info(StatusCodeConstant.STATE_UPDATE_SUCCESS);
    }
}