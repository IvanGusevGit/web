package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String handle = request.getParameter("handle");
        String password = request.getParameter("password");

        try {
            User user = userService.validateEnter(handle, password);
            setAuthorised(request, user);
        } catch (ValidationException e) {
            view.put("handle", handle);
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }

        throw new RedirectException("/index");
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
