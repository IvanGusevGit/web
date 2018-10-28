package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class News implements Serializable {
    private long userId;
    private String text;

    public News(long userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
