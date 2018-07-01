package com.kute.service;

import com.kute.BasePowerMockTest;
import com.kute.domain.User;
import com.kute.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * created by kute on 2018/06/28 22:41
 */
public class UserServiceImplTest extends BasePowerMockTest {

    @InjectMocks
    private IUserSerivce userSerivce;

    @Mock
    private RedisTemplate redisTemplate;

    @Test
    public void testFindUser() {
        String name = "kute";

        when(userSerivce.findUser(name)).thenReturn(new User(name));

        User user = userSerivce.findUser(name);

        Assert.assertNotNull(user);

    }

}
