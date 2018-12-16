package ru.itmo.webmail.web.page;


import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {

    public void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        long userId = emailConfirmationService.passEmailConfirmation(secret);
        if (userId == -1) {
            throw new RedirectException("/index", "invalidConfirmationLink");
        } else {
            userService.setConfirmed(userId);
            throw new RedirectException("/index", "confirmSuccess");
        }
    }

}
