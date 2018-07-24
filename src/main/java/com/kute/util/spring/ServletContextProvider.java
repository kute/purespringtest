package com.kute.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * created by kute on 2018/02/05 22:30
 */
@Component
public class ServletContextProvider implements ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ServletContextProvider.class);

    @Override
    public void setServletContext(ServletContext servletContext) {
        String name = "null", id = "null", contextName = "null";
        if(null != servletContext) {
            SpringContextUtil.setServletContext(servletContext);

            name = servletContext.getClass().getSimpleName();
            contextName = servletContext.getServletContextName();

            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            // maybe null
            if(null != applicationContext) {
                id = applicationContext.getId();
            }
        }
        logger.info("ServletContextAware servletContext:SimpleName={},ServletContextName={}", name, contextName);
        logger.info("ServletContextAware applicationContext:{}", id);
    }
}
