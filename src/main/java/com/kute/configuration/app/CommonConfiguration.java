package com.kute.configuration.app;

import com.kute.configuration.bean.BaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by kute on 2018/07/20 16:42
 */
@Configuration
public class CommonConfiguration {

    @Bean
    @Profile("prod") // 声明 只有当prod profile被激活时才会创建，也可以注解在类上
    public BaseBean prodBaseBean() {
        return new BaseBean("prod environment bean definition");
    }


    @Bean
    @Profile("test")
    public BaseBean testBaseBean() {
        return new BaseBean("test environment bean definition");
    }
}
