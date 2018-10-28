package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page{
    private void action() {
        //No operations
    }

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.enter(login, password);
        } catch (ValidationException e) {
            view.put("login", login);
            view.put("password", "");
            view.put("error", e.getMessage());
            return;
        }
        request.getSession().setAttribute("user", user);
        view.put("username", user.getLogin());
        throw new RedirectException("/index", "enterDone");
    }
}
