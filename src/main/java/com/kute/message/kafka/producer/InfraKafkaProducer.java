package com.kute.message.kafka.producer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kute.message.kafka.util.KafkaConfigLoader;
import com.kute.message.kafka.util.KafkaConstants;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.InvalidConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author bl
 *
 */
public class InfraKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(InfraKafkaProducer.class);

    // instance
    private static InfraKafkaProducer instance = new InfraKafkaProducer();

    private Producer<String, String> producer;

    private InfraKafkaProducer() {
        // 初始化kafka exception monitor
        logger.info("begin load kafka producer config...");

        // read configure
        Properties props = KafkaConfigLoader.loadPropertyFile(this.getClass().getClassLoader()
                .getResourceAsStream(KafkaConstants.KAFKA_PRODUCER_CONFIG_FILE));

        //初始化producer
        try {
            producer = new KafkaProducer<String, String>(props);
        } catch (InvalidConfigurationException e) {
            logger.error("KafkaException:The given config parameter has invalid values.", e);
        } catch (Exception e) {
            logger.error("KafkaException: Init producer failed.", e);
        }
        logger.info("load kafka producer config success.");
    }

    public static InfraKafkaProducer getInstance() {
        return instance;
    }

    /**
     * 发送一条message
     * 
     * @param topic
     * @param partitionKey
     * @param msgData
     * @return
     */
    public boolean sendMessage(String topic, Integer partitionKey, String key, String msgData) {
        if(Strings.isNullOrEmpty(msgData)) {
            return true;
        }
        List<String> msgList = Lists.newArrayList(msgData);
        return sendMessage(topic, partitionKey, key, msgList);
    }

    /**
     * 发送多条message
     * 
     * @param topic
     * @param partitionKey
     * @param msgDataList
     * @return
     */
    public boolean sendMessage(String topic, Integer partitionKey, String key, List<String> msgDataList) {
        if(CollectionUtils.isEmpty(msgDataList)) {
            return true;
        }
        int size = msgDataList.size();
        long sendTime = System.currentTimeMillis();
        int retry = KafkaConstants.MAX_RETRY_TIMES;
        for (int i=0; i<size; i++) {
            String msgData = msgDataList.get(i);
            while (retry-- >= 0) {
                ProducerRecord<String , String > record;
                if(null == key) {
                    record = new ProducerRecord<String, String>(topic, msgData);
                } else {
                    if(null == partitionKey) {
                        record = new ProducerRecord<String, String>(topic, key, msgData);
                    } else {
                        record = new ProducerRecord<String, String>(topic, partitionKey, key, msgData);
                    }
                }
                Future future = producer.send(record, new MsgCallBack(sendTime, i, msgData));
                try {
                    if(future.get(1, TimeUnit.SECONDS) != null) {
                        break;
                    }
                } catch (Exception e) {
                    String info = "copyToNetease tiemout: value=" + msgData + ", retry=" + retry;
                    logger.error(info);
                }
            }
        }
        return true;
    }
    
    class MsgCallBack implements Callback {
        
        private final long sendTime;
        private final int msgId;
        private final String msg;

        public MsgCallBack(long sendTime, int msgId, String msg) {
            this.sendTime = sendTime;
            this.msgId = msgId;
            this.msg = msg;
        }

        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if(null == metadata) {
                logger.error("copyToNetease from kafka send msg failed:sendTime={}, msgId={}, msg={}, error={}", new Object[]{
                        sendTime, msgId, msg, e
                });
            } else {
                logger.info("copyToNetease from kafka send msg ok:sendTime={}, msgId={}, msg={}, metadata={}", new Object[]{
                        sendTime, msgId, msg, metadata.toString()
                });
            }
        }
        
    }
}
