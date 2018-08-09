package com.kute.controller;

import com.alibaba.fastjson.JSONObject;
import com.kute.annotation.UserParam;
import com.kute.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by kute on 2018/08/09 20:09
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController extends AbstractController {

    /**
     * 自定义 参数解析
     * 前端 提交 一堆参数，只 返回 所需要的参数以及形式
     * @see
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String addUser(@UserParam User user) {

        return JSONObject.toJSONString(user);
    }

}
