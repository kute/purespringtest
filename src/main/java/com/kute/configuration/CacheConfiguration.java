package com.kute.configuration;

import com.kute.util.serializer.ProtobufRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.support.GenericMessage;
import redis.clients.jedis.JedisPoolConfig;

import java.nio.charset.Charset;

/**
 * created by kute on 2018/07/23 09:10
 */
@Configuration
public class CacheConfiguration {

    @Autowired
    private PropertyConfiguration propertyConfiguration;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
//        config.(propertyConfiguration.getProperty("redis.host"));
//        config.se(propertyConfiguration.getProperty("redis.port"));
//        config.se(propertyConfiguration.getProperty("redis.password"));
        config.setMaxIdle(propertyConfiguration.getProperty("redis.maxIdle", int.class));
        config.setTestOnBorrow(propertyConfiguration.getProperty("redis.testOnBorrow", boolean.class));
        config.setTestWhileIdle(propertyConfiguration.getProperty("redis.testWhileIdle", boolean.class));
        config.setMaxTotal(propertyConfiguration.getProperty("redis.maxTotal", int.class));
        config.setMaxWaitMillis(propertyConfiguration.getProperty("redis.maxWaitMillis", long.class));
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig());
        factory.setUsePool(true);
        factory.setHostName(propertyConfiguration.getProperty("redis.host"));
        factory.setPort(propertyConfiguration.getProperty("redis.port", int.class));
        factory.setPassword(propertyConfiguration.getProperty("redis.password"));
        return factory;
    }

    @Bean(name = "redisTemplate")
    public <T> RedisTemplate<String, T> redisTemplate() {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(stringRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(stringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

//    @Bean(name = "protobufRedisTemplate")
//    public <T extends GenericMessage> RedisTemplate<String, T> protobufRedisTemplate(Class<T> type) {
//        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setKeySerializer(stringRedisSerializer());
//        redisTemplate.setValueSerializer(protobufRedisSerializer(type));
//        redisTemplate.setHashKeySerializer(stringRedisSerializer());
//        redisTemplate.setHashValueSerializer(stringRedisSerializer());
//        return redisTemplate;
//    }

    /**
     * 序列化
     *
     * 默认提供的有：org.springframework.data.redis.serializer.*
     *
     * protobuf: com.kute.util.serializer.ProtobufRedisSerializer
     *
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer(Charset.defaultCharset());
    }

//    @Bean
//    public <T> ProtobufRedisSerializer<T> protobufRedisSerializer(Class<T> type) {
//        return new ProtobufRedisSerializer(type);
//    }

}
