package com.kute.domain;

import org.apache.commons.lang3.RandomUtils;

import java.sql.Timestamp;

/**
 * created on 2018-06-29 14:15
 */
public class User extends DomainBean {
    private static final long serialVersionUID = 7595103868406389074L;

    private Integer userId;

    private String name;

    private Timestamp date;

    public User() {
    }

    public User(String name) {
        this.userId = RandomUtils.nextInt(1, 1000);
        this.name = name;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}
