package com.kute.controller;

import com.google.common.collect.Maps;
import com.kute.configuration.PropertyConfiguration;
import com.kute.message.eventbus.MessageEventBus;
import com.kute.message.eventbus.event.MessageEvent;
import com.kute.message.queue.producer.IMessageProducer;
import com.kute.message.spring.event.CommonEvent;
import com.kute.message.spring.publisher.SpringEventPublisher;
import com.kute.util.spring.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * created by kute on 2018/04/07 12:48
 */
@Controller
@RequestMapping("/api/v1/hello")
public class HelloController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * #{}表示SpEl表达式通常用来获取bean的属性，或者调用bean的某个方法。当然还有可以表示常量
     *
     * @Value("#{1}") 数字常量
     * @Value("#{'string.constant'}")  字符串常量
     * @Value("#{myBean.property}")   bean属性
     * @Value("#{myBean.myMethod()}")  bean方法
     *
     */
    @Value("#{propertyConfiguration.appName}")
    private String appName;

    /**
     *   ?.  运算符 在调用toUpperCase方法前如果返回为null则不调用
     */
    @Value("#{propertyConfiguration.returnRandomNull()?.toUpperCase()}")
    private String randomValue;

    /**
     * @Value("#{T(myClass).propertyOrMethod}")
     * T() 运算符 访问类的静态属性或者方法
     */
    @Value("#{T(java.lang.Math).PI}")
    private String staticValue;

    @Autowired
    private MessageEventBus messageEventBus;
    @Resource
    private IMessageProducer messageProducer;
    @Autowired
    private PropertyConfiguration propertyConfiguration;

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/spring/event")
    public ResponseEntity<?> pubSpringEvent() {
        SpringEventPublisher.publish(new CommonEvent(true, "my comment", "kute"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eventbus")
    public ResponseEntity<?> pubEventBus() {
        messageEventBus.post(new MessageEvent("message event post"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/mq")
    public ResponseEntity<?> telnetMq(
            @RequestParam(value = "type", required = false, defaultValue = "direct") String type
    ) {
        logger.info("telnet rabbitmq[{}] on localhost:5672", type);
        if("direct".equalsIgnoreCase(type)) {
            messageProducer.sendDirectMessage("hello-direct-message");
        } else if("fanout".equalsIgnoreCase(type)) {
            messageProducer.sendFanoutMessage("hello-fanout-message");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param key
     *      .+ 主要解决 路径参数有.特殊符号
     * @return
     */
    @GetMapping("/property/{key:.+}")
    public ResponseEntity<?> getProperty(@PathVariable("key") String key) {
        logger.info("get property");
        Map<String, Object> map = Maps.newHashMap();
        map.put("a", appName);
        map.put("b", propertyConfiguration.getProperty(key));
        map.put("c", propertyConfiguration.appName);
        map.put("d", Optional.ofNullable(randomValue).orElse("null"));
        map.put("e", staticValue);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/env")
    public ResponseEntity<?> env() {
        ApplicationContext applicationContext = SpringContextUtil.applicationContext;
        Map<String, Object> map = Maps.newHashMap();
        map.put("activeProfiles", Arrays.toString(applicationContext.getEnvironment().getActiveProfiles()));
        map.put("defaultProfiles", Arrays.toString(applicationContext.getEnvironment().getDefaultProfiles()));
        map.put("beanDefinitionCount", applicationContext.getBeanDefinitionCount());
        map.put("beanDefinitionNames", Arrays.toString(applicationContext.getBeanDefinitionNames()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
