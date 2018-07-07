package com.kute.message.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * created on 2018-07-05 15:06
 */
public abstract class AbstractQueue {

    protected <T> T parseMessage(Message message) throws Exception {
        Jackson2JsonMessageConverter jmc = new Jackson2JsonMessageConverter();
        return JSON.parseObject(JSON.toJSONString(jmc.fromMessage(message)), new TypeReference<T>() {
        });
    }

}
