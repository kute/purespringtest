package com.kute.domain;

import com.kute.BasePowerMockTest;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest({User.class})
public class UserTest extends BasePowerMockTest {

    private static String name = "kute";

    @Test
    public void toUpperCaseName() throws Exception {

        User user = new User(name);

        mockStatic(User.class);

        doNothing().when(User.class, "toUpperCaseName", user);

        User.toUpperCaseName(user);

        assertEquals(name.toUpperCase(), user.getName());

    }

}