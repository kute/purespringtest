package com.kute.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public static String toUpperCaseName(User user) {
        if(!Strings.isNullOrEmpty(user.getName())) {
            user.setName(user.getName().toUpperCase());
        }
        return user.getName();
    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Timestamp getDate() {
        return date;
    }

    public User setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("name", name)
                .add("date", date)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(userId, user.userId)
                .append(name, user.name)
                .append(date, user.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(name)
                .append(date)
                .toHashCode();
    }
}
