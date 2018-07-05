package com.kute.message.queue.producer.impl;

import com.kute.message.queue.producer.IMessageProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * created on 2018-07-05 15:24
 */
@Service("messageProducer")
public class MessageProducerImpl implements IMessageProducer {

    @Resource
    private AmqpTemplate rabbitTemplate;

    @Override
    public void sendDefaultMessage(String message) {
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("message", message);
        rabbitTemplate.convertAndSend("default_queue", paramMap);
    }
}
