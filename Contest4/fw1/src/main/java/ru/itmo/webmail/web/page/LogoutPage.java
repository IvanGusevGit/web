package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage extends Page{

    private void action(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().removeAttribute("user");
        view.remove("username");
        throw new RedirectException("/index", "logoutDone");
    }

}
