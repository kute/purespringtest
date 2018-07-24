package com.kute.util.spring;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * created by kute on 2018/04/07 20:47
 */
public class SpringContextUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    public static ApplicationContext applicationContext;

    private static ServletContext servletContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if(null != applicationContext) {
            logger.info("SpringContextUtil setApplicationContext:{}", applicationContext.getId());
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static void setServletContext(ServletContext servletContext) {
        if(null != servletContext) {
            logger.info("SpringContextUtil setServletContext:{}", servletContext.getClass().getSimpleName());
            SpringContextUtil.servletContext = servletContext;
        }
    }

    public static <T> T getBean (Class<T> clazz) {
        Preconditions.checkNotNull(applicationContext);
        Preconditions.checkNotNull(clazz);
        return applicationContext.getBean(clazz);
    }
}
