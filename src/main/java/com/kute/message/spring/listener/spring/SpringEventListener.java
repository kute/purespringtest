package com.kute.message.spring.listener.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * 监听 spring 自带事件
 * created by kute on 2018/04/15 10:46
 */
@Component
public class SpringEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SpringEventListener.class);

    @EventListener
    public void onContextRefreshedEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("onContextRefreshedEvent:{}", contextRefreshedEvent);
    }

    @EventListener
    public void onContextStartedEvent(ContextStartedEvent contextStartedEvent) {
        logger.debug("onContextStartedEvent:{}", contextStartedEvent);
    }

    @EventListener
    public void onContextStoppedEvent(ContextStoppedEvent contextStoppedEvent) {
        logger.debug("onContextStoppedEvent:{}", contextStoppedEvent);
    }

    @EventListener
    public void onContextClosedEvent(ContextClosedEvent contextClosedEvent) {
        logger.debug("onContextClosedEvent:{}", contextClosedEvent);
    }

    @EventListener
    public void onRequestHandledEvent(RequestHandledEvent requestHandledEvent) {
        logger.debug("onRequestHandledEvent:{}", requestHandledEvent);
    }

    @EventListener
    public void onServletRequestHandledEvent(ServletRequestHandledEvent servletRequestHandledEvent) {
        logger.debug("onServletRequestHandledEvent:{}", servletRequestHandledEvent);
    }

}
