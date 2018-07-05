package com.kute.service;

import com.google.common.collect.Lists;
import com.kute.BasePowerMockTest;
import com.kute.service.impl.CommentServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * created on 2018-06-29 15:00
 */
@PrepareForTest({CommentServiceImpl.class})
public class CommentServiceImplTest extends BasePowerMockTest {

    @Test
    public void testGetUserCommentList() {

        Integer userId = Integer.MAX_VALUE;

        CommentServiceImpl commentService = PowerMockito.mock(CommentServiceImpl.class);

        when(commentService.getUserCommentList(userId)).thenReturn(Lists.newArrayList(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10)
        ));

        List<String > userCommentList = commentService.getUserCommentList(userId);

        LOGGER.info("{}", userCommentList);

        Assert.assertNotNull(userCommentList);

    }

}
