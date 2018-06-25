package com.kute.service.impl;

import com.kute.service.IUserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * created by kute on 2018/04/17 22:33
 */
@Service("userService")
public class UserServiceImpl implements IUserSerivce {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Long> hashOperations;


    public void test() {

        redisTemplate.execute((RedisConnection redisConnection) -> {
            return null;
        });

        // with transaction
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                //...
                return redisOperations.exec();
            }
        });

        redisTemplate.opsForValue().set("a", "v", 10, TimeUnit.DAYS);

        redisTemplate.executePipelined((RedisConnection redisConnection) -> {
            return null;
        });


    }

}
