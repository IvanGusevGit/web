package ru.itmo.webmail.model.domain;

public class EmailConfirmation extends Item {
    private long userId;
    private String secret;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIdString() {
        return Long.toString(userId);
    }

    public void setUserIdString(String userId) {
        setUserId(Long.parseLong(userId));
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecretString() {
        return secret;
    }

    public void setSecretString(String secret) {
        setSecret(secret);
    }

}
