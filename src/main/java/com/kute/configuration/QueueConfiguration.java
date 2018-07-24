package com.kute.configuration;

import com.kute.message.queue.consumer.DefaultChannelAwareMessageListener;
import com.kute.message.queue.consumer.DirectQueueListener;
import com.kute.util.MessageQueueDefinitionEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.kute.util.MessageQueueDefinitionEnum.*;

import java.nio.charset.Charset;

/**
 * created by kute on 2018/07/23 09:28
 */
@Configuration
public class QueueConfiguration {

    @Autowired
    private PropertyConfiguration propertyConfiguration;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(propertyConfiguration.getProperty("rabbitmq.host"));
        connectionFactory.setPort(propertyConfiguration.getProperty("rabbitmq.port", int.class));
        connectionFactory.setUsername(propertyConfiguration.getProperty("rabbitmq.username"));
        connectionFactory.setPassword(propertyConfiguration.getProperty("rabbitmq.password"));
        connectionFactory.setVirtualHost(propertyConfiguration.getProperty("rabbitmq.vhost"));
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "directRabbitTemplate")
    public RabbitTemplate directRabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(DIRECT_QUEUE.getExchange());
        rabbitTemplate.setEncoding(Charset.defaultCharset().name());
        return rabbitTemplate;
    }

    @Bean(name = "fanoutRabbitTemplate")
    public RabbitTemplate fanoutRabbitTemplate(ConnectionFactory connectionFactory,
                                               Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(FANOUT_QUEUE.getExchange());
        rabbitTemplate.setEncoding(Charset.defaultCharset().name());
        return rabbitTemplate;
    }

    @Bean(name = "direct_queue")
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE.getQueueName(), true, false, false);
    }

    @Bean(name = "fanout_queue")
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE.getQueueName(), true, false, false);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_QUEUE.getExchange(), true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_QUEUE.getExchange(), true, false);
    }

    @Bean
    public Binding directQueueBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(MessageQueueDefinitionEnum.DIRECT_QUEUE.getQueueKey());
    }

    @Bean
    public Binding fanoutQueueBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public MessageListener directQueueMessageListener() {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(directQueueListener(), messageConverter());
        listenerAdapter.setDefaultListenerMethod("onMessage");
        return listenerAdapter;
    }

    @Bean
    public SimpleMessageListenerContainer directSimpleMessageListenerContainer(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter,
            @Qualifier("direct_queue") Queue directQueue,
            @Qualifier("directQueueMessageListener") MessageListener directQueueMessageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setPrefetchCount(20);
        container.setConcurrentConsumers(10);
        container.setMessageConverter(messageConverter);
        container.setMessageListener(directQueueMessageListener);
        container.setQueues(directQueue);
        return container;
    }

    @Bean
    public MessageListener fanoutQueueMessageListener() {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(defaultChannelAwareMessageListener(), messageConverter());
        listenerAdapter.setDefaultListenerMethod("onMessage");
        return listenerAdapter;
    }

    @Bean
    public SimpleMessageListenerContainer fanoutSimpleMessageListenerContainer(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter,
            @Qualifier("fanout_queue") Queue fanoutQueue,
            @Qualifier("fanoutQueueMessageListener") MessageListener fanoutQueueMessageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setPrefetchCount(20);
        container.setConcurrentConsumers(10);
        container.setMessageConverter(messageConverter);
        container.setMessageListener(fanoutQueueMessageListener);
        container.setQueues(fanoutQueue);
        return container;
    }

    /**
     * 实现类
     * @return
     */
    @Bean
    public DirectQueueListener directQueueListener() {
        return new DirectQueueListener();
    }

    @Bean
    public DefaultChannelAwareMessageListener defaultChannelAwareMessageListener() {
        return new DefaultChannelAwareMessageListener();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactoryBean) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactoryBean);
        admin.setIgnoreDeclarationExceptions(true); //这样即使有关rabbitmq的bean初始化失败整个web应用还能正常启动
        return admin;
    }
}
