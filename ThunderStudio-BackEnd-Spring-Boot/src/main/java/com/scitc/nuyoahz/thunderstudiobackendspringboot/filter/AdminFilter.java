package com.scitc.nuyoahz.thunderstudiobackendspringboot.filter;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;
import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CookieUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Nuyoahz
 * @date 2024/1/11 17:22
 */
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 请求路径
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("/ + $", "");

        switch ((Integer) CookieUtils.analyzeCookie(request.getCookies()).get("authority")) {
            case 0:
                if (SystemConstant.ADMIN_PATH.contains(path)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(403);
                }
                break;
            case 1:
                if (SystemConstant.PERSON_IN_CHARGE_PATH.contains(path)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(403);
                }
                break;
            case 2:
                if (SystemConstant.EVERY_MAN.contains(path)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(403);
                }
                break;
            default:
                response.setStatus(403);
        }

    }
}
