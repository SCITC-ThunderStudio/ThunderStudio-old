package com.scitc.nuyoahz.thunderstudiobackendspringboot.model.dto;

import lombok.Getter;

import java.util.HashMap;

/**
 * 路由
 *
 * @author Nuyoahz
 * @date 2024/1/11
 */
@Getter
public class RouterList {
    /**
     * 索引
     */
    private final Integer id;
    /**
     * 路径
     */
    private final String path;

    /**
     * 路由名字
     */
    private final String name;

    /**
     * 路由配置
     */
    private final HashMap<String, String> meta;

    public RouterList(Integer id, String path, String name, HashMap<String, String> meta) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.meta = meta;
    }
}
