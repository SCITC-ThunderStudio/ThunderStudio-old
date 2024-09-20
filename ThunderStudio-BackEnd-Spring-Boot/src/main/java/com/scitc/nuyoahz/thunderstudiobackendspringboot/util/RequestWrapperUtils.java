package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartRequest;

import java.io.*;
import java.util.*;

/**
 * 请求体工具
 *
 * @author Nuyoahz
 * @date 2024/2/23 22:39
 */
public class RequestWrapperUtils extends HttpServletRequestWrapper {
    private static final Logger log = LoggerFactory.getLogger(RequestWrapperUtils.class);

    /**
     * 原始Request对象
     */
    private final HttpServletRequest request;

    /**
     * 额外参数可以加到这个里面
     */
    private final Map<String, String[]> parameterMap = new LinkedHashMap<>();

    /**
     * 请求体
     */
    @Getter
    private String requestBody;

    public RequestWrapperUtils(HttpServletRequest request) {
        super(request);
        this.request = request;
        // 如果是文件上传类请求
        if (request instanceof MultipartRequest) {
            this.parseBody(request);
        } else {
            // 普通请求将请求体设置到Body中去;这样可以多次使用流方式访问请求体
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                IOUtils.copy(request.getInputStream(), byteArrayOutputStream);
                requestBody = byteArrayOutputStream.toString();
            } catch (IOException e) {
                log.error("复制请求体失败,错误信息: {}", e.getMessage());
            }
        }
    }

    /**
     * 如果是MultipartRequest，需要解析参数信息
     *
     * @param request 请求体
     * @author Nuyoahz
     * @date 2024/02/24 18:54
     */
    private void parseBody(HttpServletRequest request) {
        Map<String, Object> parameterMap = new LinkedHashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            parameterMap.put(name, (values != null && values.length == 1) ? values[0] : values);
        }
        // 将解析出来的参数，转换成JSON并设置到Body中保存
        this.requestBody = JSONUtil.toJsonStr(parameterMap);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


    /**
     * 设置请求体，如果是MultipartRequest请求则需要同步保存到参数Map中去
     *
     * @param requestBody 请求体
     */
    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        try {
            if (this.request instanceof MultipartRequest) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = objectMapper.readValue(requestBody, Map.class);
                this.setParameterMap(map);
            }
        } catch (Exception e) {
            log.error("转换参数异常,参数: {},错误信息: {}", requestBody, e.getMessage());
        }
    }

    @Override
    public String getParameter(String name) {
        String result = super.getParameter(name);
        // 如果参数获取不到则尝试从参数Map中获取，并且只返回第一个
        if (StringUtils.isBlank(result) && this.parameterMap.containsKey(name)) {
            result = this.parameterMap.get(name)[0];
        }
        return result;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        // 需要将原有的参数加上新参数 返回
        Map<String, String[]> map = new HashMap<>(super.getParameterMap());
        for (String key : this.parameterMap.keySet()) {
            map.put(key, this.parameterMap.get(key));
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * 设置参数map
     *
     * @param json2map
     * @author Nuyoahz
     * @date 2024/02/24 18:56
     */
    public void setParameterMap(Map<String, Object> json2map) {
        if (json2map != null && !json2map.isEmpty()) {
            for (String key : json2map.keySet()) {
                String value = MapUtils.getString(json2map, key);
                if (this.parameterMap.containsKey(key)) {
                    this.parameterMap.put(key, ArrayUtils.add(this.parameterMap.get(key), value));
                } else {
                    this.parameterMap.put(key, new String[]{value});
                }
            }
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = super.getParameterValues(name);
        if (result == null && this.parameterMap.containsKey(name)) {
            result = this.parameterMap.get(name);
        }
        return result;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> parameterNames = super.getParameterNames();
        Set<String> names = new LinkedHashSet<>();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                names.add(parameterNames.nextElement());
            }
        }
        // 添加后期设置的参数Map
        if (!this.parameterMap.isEmpty()) {
            names.addAll(this.parameterMap.keySet());
        }
        return Collections.enumeration(names);
    }
}