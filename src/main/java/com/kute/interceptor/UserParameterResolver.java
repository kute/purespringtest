package com.kute.interceptor;

import com.kute.annotation.UserParam;
import com.kute.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * created by kute on 2018/08/09 20:16
 * 自定义参数解析
 */
public class UserParameterResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(UserParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String name = request.getParameter("name");
        String time = request.getParameter("time");
        Timestamp timestamp = Timestamp.valueOf(time);
        return new User(name, timestamp);
    }
}
