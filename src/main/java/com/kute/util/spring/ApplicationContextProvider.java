package com.kute.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * created by kute on 2018/04/07 20:52
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextProvider.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String id = "null";
        if(null != applicationContext) {
            id = applicationContext.getId();
            SpringContextUtil.setApplicationContext(applicationContext);
        }
        logger.info("ApplicationContextAware setApplicationContext:{}", id);
    }
}
