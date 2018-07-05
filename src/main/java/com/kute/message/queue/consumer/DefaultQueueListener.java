package com.kute.message.queue.consumer;

import com.kute.message.queue.AbstractQueue;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

/**
 * created on 2018-07-05 15:07
 */
public class DefaultQueueListener extends AbstractQueue implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQueueListener.class);

    @Override
    public void onMessage(Message message) {

        try {
            Map<String, Object> messageMap = parseMessage(message);
            if (MapUtils.isNotEmpty(messageMap)) {
                // process message
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }
}
