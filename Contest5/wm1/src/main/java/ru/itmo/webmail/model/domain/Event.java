package ru.itmo.webmail.model.domain;


public class Event extends Item {
    private long userId;
    private EventType type;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIdString() {
        return Long.toString(userId);
    }

    public void setUserIdString(String userIdString) {
        setUserId(Long.parseLong(userIdString));
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getTypeString() {
        return type.toString();
    }

    public void setTypeString(String typeString) {
        if ("ENTER".equalsIgnoreCase(typeString)) {
            setType(EventType.ENTER);
        } else {
            setType(EventType.LOGOUT);
        }
    }


    public enum EventType {
        ENTER,
        LOGOUT
    }
}
