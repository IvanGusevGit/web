package ru.itmo.webmail.model.domain;

public class User extends Item {
    private String login;
    private String email;
    private boolean confirmed;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginString() {
        return login;
    }

    public void setLoginString(String value) {
        setLogin(value);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailString() {
        return email;
    }

    public void setEmailString(String value) {
        setEmail(value);
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getConfirmedString() {
        return confirmed ? "1" : "0";
    }

    public void setConfirmedString(String value) {
        setConfirmed("1".equals(value) || "true".equalsIgnoreCase(value));
    }
}
