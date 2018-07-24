package com.kute.configuration.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * created by kute on 2018/07/20 19:15
 */
@Component
@Scope(
        value = ConfigurableBeanFactory.SCOPE_SINGLETON,
        // 当注入 session bean时，session bean延迟初始化，所以当单例bean在spring上下文初始化时会自动注入一个代理，proxyMode解决的是生成代理的方式：基于接口(jdk)or基于类(cglib)
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class SingletonBean {

    private SessionBean sessionBean;

    @Autowired
    public SingletonBean setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        return this;
    }
}
