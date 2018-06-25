package com.kute.message.spring.event;

import com.google.common.base.MoreObjects;

/**
 * created by kute on 2018/04/15 10:39
 */
public class CommonEvent {

    private Boolean monitor;

    private String comment;

    private String user;

    public CommonEvent() {
    }

    public CommonEvent(Boolean monitor, String comment, String user) {
        this.monitor = monitor;
        this.comment = comment;
        this.user = user;
    }

    public Boolean getMonitor() {
        return monitor;
    }

    public CommonEvent setMonitor(Boolean monitor) {
        this.monitor = monitor;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommonEvent setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getUser() {
        return user;
    }

    public CommonEvent setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("monitor", monitor)
                .add("comment", comment)
                .add("user", user)
                .toString();
    }

}
