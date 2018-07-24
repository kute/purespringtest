package com.kute.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * created by kute on 2018/07/20 16:48
 */
@Configuration
@ComponentScan(
        basePackages = {"com.kute"},
        // 排除 WebConfiguration类的扫描范围
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
)
public class RootConfiguration {
}
