package com.kute.exception;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by kute on 2018/04/07 12:49
 */
public class ExceptionResolver implements HandlerExceptionResolver, Ordered {
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public ModelAndView resolveException(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) {
        ModelMap modelMap = new ModelMap();
        modelMap.clear();
        return resolve(request, response, modelMap, ex);
    }

    private ModelAndView resolve(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, Exception ex) {
        String callBack = request.getParameter("callback");
        if(Strings.isNullOrEmpty(callBack)) {
            callBack = request.getParameter("jsoncallback");
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(Charsets.UTF_8.name());

        try {
            String res = JSONObject.toJSONString(modelMap);
            if (!Strings.isNullOrEmpty(callBack)) {
                response.getWriter().write(new StringBuilder(callBack).append("(").append(res).append(")").toString());
            } else {
                response.getWriter().write(res);
            }
        } catch (IOException e1) {
            logger.error("process error response", e1);
        }
        return new ModelAndView();
    }
}
