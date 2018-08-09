package com.kute.interceptor;

import com.kute.annotation.JsonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * created by kute on 2018/08/09 20:24
 *
 * 自定义 返回 数据 解析
 *
 * 类似如   @ResponseBody 的处理器 RequestResponseBodyMethodProcessor 实现了 HandlerMethodReturnValueHandler 接口
 */
public class JsonReturnResolver implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(JsonResponse.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {

    }
}
