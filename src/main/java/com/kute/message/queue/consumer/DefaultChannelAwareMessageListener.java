package com.kute.message.queue.consumer;

import com.kute.message.queue.AbstractQueue;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * created on 2018-07-05 15:34
 *
 * https://www.cnblogs.com/piaolingzxh/p/5448927.html
 *
 */
public class DefaultChannelAwareMessageListener extends AbstractQueue implements ChannelAwareMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultChannelAwareMessageListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        LOGGER.info("Receive message from fanoutQueue:{}", message);
        long tagId = message.getMessageProperties().getDeliveryTag();
        try {
        } catch(Exception e) {
            e.printStackTrace();
            LOGGER.error("", e);
        } finally {

            /**
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
             */
            channel.basicAck(tagId, false);

            /**
             * deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
             * requeue：被拒绝的是否重新入队列
             */
//            channel.basicNack(tagId, false, true);

            /**
             * deliveryTag:该消息的index
             * requeue：被拒绝的是否重新入队列
             * channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消
             */
//            channel.basicReject(tagId, true);

        }
    }

}
