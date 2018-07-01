package com.kute.util;

import com.google.common.collect.Lists;
import com.kute.BasePowerMockTest;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @PrepareForTest：模拟构造函数、私有方法、static方法、final方法都需要在测试类上添加注解
 */
@PrepareForTest({StringUtil.class})
public class StringUtilTest extends BasePowerMockTest {

    private StringUtil stringUtilMock;

    public void before() {

    }

    @Test
    public void toList() throws Exception {

        String sequence = "sre";

        mockStatic(StringUtil.class);

        List<String> expected = Lists.newArrayList(sequence);

        // 有返回值的静态方法
        when(StringUtil.toList(sequence)).thenReturn(expected);

        List<String> result = StringUtil.toList(sequence);

        assertEquals(expected, result);

        verifyStatic(StringUtil.class);

        StringUtil.toList(sequence);

    }
}