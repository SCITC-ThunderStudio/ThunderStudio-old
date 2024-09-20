package com.scitc.nuyoahz.thunderstudiobackendspringboot.filter;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

/**
 * 全局过滤器
 *
 * @author Nuyoahz
 * @date 2024/1/11 16:42
 */
@Log4j2
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 请求路径
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("/ + $", "");
        log.info("请求路径:{}", path);
        log.info("会话id:{}", request.getHeader(SystemConstant.HEART_SESSION));
        // 如果在白名单内则直接放行
        if (SystemConstant.REQUEST_WHITEE_LIST.contains(path)) {
            log.info("请求路径在白名单内,直接放行");
            filterChain.doFilter(request, response);
            // 请求数据解密
        } else if (SystemConstant.IS_ENCRYPT.equals(request.getHeader(SystemConstant.HEART_ENCRYPT))) {
            log.info("请求数据解密");
            RequestWrapperUtils requestWrapper = new RequestWrapperUtils(request);
            // 获取密钥和偏移量
            List<String> aesKey = CommonUtils.getAESKey(request);
            log.info("密钥:{}, 偏移量:{}", aesKey.get(0), aesKey.get(1));
            // 初始化AES
            AESUtils aesUtils = new AESUtils(aesKey.get(0), aesKey.get(1));
            // 获取加密之前的请求数据
            String encryptData = requestWrapper.getRequestBody();
            log.info("待解密的请求参数:{}", encryptData);
            String decryptData = aesUtils.decrypt(encryptData);
            log.info("解密后的请求参数:{}", decryptData);
            requestWrapper.setRequestBody(decryptData);
            filterChain.doFilter(requestWrapper, response);
            // 如果没有Session-ID或已经过期则直接返回401
        } else if (request.getHeader(SystemConstant.HEART_SESSION) == null || !RedisUtils.hasKey(request.getHeader(SystemConstant.HEART_SESSION))) {
            log.info("登录过期");
            response.setStatus(401);
        } else {
            log.info("数据没有加密,直接放行");
            // 数据没有加密直接放行
            filterChain.doFilter(request, response);
        }
    }
}