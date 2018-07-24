package com.kute.message.spring.listener;

import com.google.common.eventbus.DeadEvent;
import com.kute.message.spring.event.CommonEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * created by kute on 2018/04/15 10:22
 */
@Component
// 事件监听异步
@EnableAsync
public class EventBaseListener {

    private static final Logger logger = LoggerFactory.getLogger(EventBaseListener.class);

    /**
     * 监听 CommonEvent 事件, 只有当 事件monitor属性为true时才触发
     * @param commonEvent
     */
    @EventListener(condition = "#commonEvent.monitor")
    public void OnCommentEventWhenMonitorTrue(CommonEvent commonEvent) {
        logger.debug("Receive CommonEvent when commonEvent.monitor=true:{}", commonEvent);
    }

    /**
     * 监听 CommonEvent 事件
     * @param commonEvent
     */
    @EventListener
    @Async("defaultExecutor")
    public void OnCommentEvent(CommonEvent commonEvent) {
        logger.debug("Receive CommonEvent:{}", commonEvent);
    }

    /**
     * 监听任何event
     * @param event
     */
    @EventListener
    public void OnEvent(Object event) {
        logger.debug("Receive Event:{}", event);
    }

    /**
     * 当事件类型为 DeadEvent 时触发
     * @param deadEvent
     */
    @EventListener(classes = {DeadEvent.class})
    public void OnDeadEventWhen(DeadEvent deadEvent) {
        logger.debug("Receive DeadEvent:{}", deadEvent);
    }

    /*----------------------------------------------------------*/

}
