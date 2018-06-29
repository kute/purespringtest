package com.kute.service.impl;

import com.google.common.collect.Lists;
import com.kute.service.AbstractService;
import com.kute.service.ICommentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created on 2018-06-29 14:58
 */
@Service("commentService")
public class CommentServiceImpl extends AbstractService implements ICommentService {
    @Override
    public List<String> getUserCommentList(Integer userId) {
        return Lists.newArrayList(
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10)
        );
    }
}
