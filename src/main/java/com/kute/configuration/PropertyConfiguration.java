package com.kute.configuration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * created by kute on 2018/07/20 17:11
 */
@Configuration
@PropertySources({
        @PropertySource(
                value = {
                        "classpath:config.properties",
                        "classpath:redis.properties",
                        "classpath:rabbitmq.properties"
                },
                encoding = "utf-8"
        )
})
public class PropertyConfiguration {

    @Autowired
    private Environment environment;

    // 被 springmvc扫描注册的文件才可使用$Value
    @Value("${app.name}")
    public String appName;

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    // key不存在抛IllegalStateException
    public String getRequiredProperty(String key) {
        return environment.getRequiredProperty(key);
    }

    public <T> T getProperty(String key, Class<T> clazz) {
        return environment.getProperty(key, clazz);
    }

    public String returnRandomNull() {
        if(RandomUtils.nextInt(0, 3) > 1) {
            return "random null value";
        }
        return null;
    }
}
