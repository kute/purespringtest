package com.kute.message.queue.producer;

public interface IMessageProducer {

    /**
     * sent direct message
     * @param message
     */
    void sendDirectMessage(String message);

    /**
     *
     * @param message
     */
    void sendFanoutMessage(String message);

}
