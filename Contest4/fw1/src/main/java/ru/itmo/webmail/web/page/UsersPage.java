package ru.itmo.webmail.web.page;

import java.util.Map;

public class UsersPage extends Page {

    protected void action(Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

}
