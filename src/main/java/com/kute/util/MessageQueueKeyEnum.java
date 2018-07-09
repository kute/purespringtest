package com.kute.util;

/**
 * created by kute on 2018/07/08 10:41
 */
public enum MessageQueueKeyEnum {

    DEFAULT_MESSAGE("default_queue_key");

    private String key;

    MessageQueueKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
