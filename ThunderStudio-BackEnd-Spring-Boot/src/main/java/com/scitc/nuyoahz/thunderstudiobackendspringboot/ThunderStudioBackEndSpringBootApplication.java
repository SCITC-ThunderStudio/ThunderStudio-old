package com.scitc.nuyoahz.thunderstudiobackendspringboot;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.util.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 主启动类
 *
 * @author nuyoahz
 * @date 2023/12/10
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.scitc.nuyoahz.thunderstudiobackendspringboot.mapper")
@Log4j2
public class ThunderStudioBackEndSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThunderStudioBackEndSpringBootApplication.class, args);
        log.info("Jar Package Run Path {}", CommonUtils.getJarAbsolutePath());
    }
}