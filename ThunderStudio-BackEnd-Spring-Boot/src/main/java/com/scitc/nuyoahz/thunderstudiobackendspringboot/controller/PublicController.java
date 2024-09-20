package com.scitc.nuyoahz.thunderstudiobackendspringboot.controller;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.StatusCodeConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.exception.ApiException;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto.*;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.AnnouncementServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.service.impl.UserServiceImpl;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.RSAUtiles;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.RedisUtils;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.VerifyUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公共接口
 *
 * @author Nuyoahz
 * @date 2024/1/9
 */
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Log4j2
public class PublicController {
    private final UserServiceImpl userService;
    private final AnnouncementServiceImpl announcementService;

    /**
     * 登陆接口
     *
     * @param loginForm 登陆表单
     * @return ResponseData<String>
     * @author Nuyoahz
     * @date 2024/4/22
     */
    @PostMapping("/login")
    public ResponseData<String> login(@RequestBody LoginForm loginForm) {
        return ResponseData.info(StatusCodeConstant.LOGIN_SUCCESS, userService.login(loginForm));
    }

    /**
     * 登出接口
     *
     * @param request 请求
     * @return Response
     * @author Nuyoahz
     * @date 2024/01/11
     */
    @GetMapping("/loginOut")
    public Response loginOut(HttpServletRequest request) {
        String sessionId = request.getHeader(SystemConstant.HEART_SESSION);
        if (RedisUtils.deleteKey(sessionId)) {
            return Response.info(StatusCodeConstant.LOGIN_OUT_SUCCESS);
        } else {
            log.warn("sessionId删除失败 {}", sessionId);
            return Response.info(StatusCodeConstant.LOGIN_OUT_ERROR);
        }
    }

    /**
     * 获取RSA公钥接口
     *
     * @return ResponseData<String>
     * @author Nuyoahz
     * @date 2024/01/12
     */
    @GetMapping("/getRSAPublicKey")
    public ResponseData<String> getRSAPublicKey() {
        return ResponseData.info(StatusCodeConstant.SUCCESS, RSAUtiles.getPublicKey());
    }

    /**
     * 获取验证码接口
     *
     * @param uid   用户id
     * @param email 邮箱
     * @return Response
     * @author Nuyoahz
     * @date 2024/01/11
     */
    @GetMapping("/getCaptcha")
    public Response getCaptcha(@RequestParam("uid") Integer uid, @RequestParam("email") String email) {
        if (!VerifyUtils.isEmail(email)) {
            ApiException.setError(StatusCodeConstant.EMAIL_FORMAT_ERROR);
        }
        userService.getCaptcha(uid, email);
        return Response.info(StatusCodeConstant.CAPTCHA_SENT_SUCCESS);
    }

    /**
     * 修改密码接口(未登录)
     *
     * @param updatePasswordFForm 更新密码表单
     * @return Response
     * @author Nuyoahz
     * @date 2024/01/12
     */
    @PutMapping("/updatePasswordF")
    public Response updatePasswordF(@RequestBody UpdatePasswordFForm updatePasswordFForm) {
        if (!VerifyUtils.isPassword(updatePasswordFForm.newPassword())) {
            ApiException.setError(StatusCodeConstant.PASSWORD_FORMAT_ERROR);
        }
        userService.updatePassword(updatePasswordFForm);
        return Response.info(StatusCodeConstant.PASSWORD_UPDATE_SUCCESS);
    }

    /**
     * 获取概览接口
     *
     * @return ResponseData<Overview>
     * @author Nuyoahz
     * @date 2024/2/05
     */
    @GetMapping("/getOverview")
    public ResponseData<Overview> getOverview() {
        return ResponseData.info(StatusCodeConstant.SUCCESS, userService.getOverview());
    }

    /**
     * 获取做题数前十排名接口
     *
     * @return ResponseData<List < Rank>>
     * @author Nuyoahz
     * @date 2024/2/05
     */
    @GetMapping("/getRank")
    public ResponseData<List<Rank>> getRank() {
        return ResponseData.info(StatusCodeConstant.SUCCESS, userService.getRank());
    }

    /**
     * 获取公告接口(最新的五条)
     *
     * @return ResponseData<List < AnnouncementList>>
     * @author Nuyoahz
     * @date 2024/4/22
     */
    @GetMapping("/getAnnouncement")
    public ResponseData<List<AnnouncementList>> getAnnouncement() {
        return ResponseData.info(announcementService.getAnnouncement());
    }
}