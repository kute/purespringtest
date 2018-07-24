package com.kute.message.queue.producer.impl;

import com.kute.message.queue.producer.IMessageProducer;
import com.kute.util.MessageQueueDefinitionEnum;
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
    private AmqpTemplate directRabbitTemplate;

    @Resource
    private AmqpTemplate fanoutRabbitTemplate;

    @Override
    public void sendDirectMessage(String message) {
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("message", message);
        directRabbitTemplate.convertAndSend(MessageQueueDefinitionEnum.DIRECT_QUEUE.getQueueKey(), paramMap);
    }

    @Override
    public void sendFanoutMessage(String message) {
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("message", message);
        fanoutRabbitTemplate.convertAndSend(MessageQueueDefinitionEnum.FANOUT_QUEUE.getQueueKey(), paramMap);
    }

}
