package com.kute.util;

/**
 * created by kute on 2018/07/08 10:41
 */
public enum MessageQueueDefinitionEnum {

    DIRECT_QUEUE("directExchange", "direct_queue", "direct_queue_key"),

    FANOUT_QUEUE("fanoutExchange", "fanout_queue", "fanout_queue_key");

    private final String exchange;
    private final String queueName;
    private final String queueKey;

    MessageQueueDefinitionEnum(String exchange, String queueName, String queueKey) {
        this.exchange = exchange;
        this.queueName = queueName;
        this.queueKey = queueKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getQueueKey() {
        return queueKey;
    }
}
