package com.kute.message.spring.publisher;

import com.google.common.base.Preconditions;
import com.kute.util.spring.SpringContextUtil;

/**
 * created by kute on 2018/04/15 10:41
 */
public class SpringEventPublisher {

    public static void publish(Object event) {
        Preconditions.checkNotNull(event);
        SpringContextUtil.applicationContext.publishEvent(event);
    }
}
