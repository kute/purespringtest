package com.kute.util.spring;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

/**
 * created by kute on 2018/04/08 22:58
 */
public class CustomAutoProxy extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, TargetSource targetSource) throws BeansException {
        return new Object[0];
    }

}
