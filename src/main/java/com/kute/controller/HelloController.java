package com.kute.controller;

import com.kute.message.eventbus.MessageEventBus;
import com.kute.message.eventbus.event.MessageEvent;
import com.kute.message.spring.event.CommonEvent;
import com.kute.message.spring.publisher.SpringEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by kute on 2018/04/07 12:48
 */
@Controller
@RequestMapping("/api/v1/hello")
public class HelloController {

    @Autowired
    private MessageEventBus messageEventBus;

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/spring/event")
    public ResponseEntity<?> pubSpringEvent() {
        SpringEventPublisher.publish(new CommonEvent(true, "my comment", "kute"));
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/eventbus")
    public ResponseEntity<?> pubEventBus() {
        messageEventBus.post(new MessageEvent("message event post"));
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
