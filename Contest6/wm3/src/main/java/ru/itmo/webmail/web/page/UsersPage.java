package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersPage extends Page{
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (user == null) {
            throw new RedirectException("/index");
        }
    }

    private List<User> find(HttpServletRequest request, Map<String, Object> view) {
        return getUserService().findAll();
    }

    private boolean isAdmin(HttpServletRequest request, Map<String, Object> view) {
        user = userService.find((Long)request.getSession().getAttribute(USER_ID_SESSION_KEY));
        return user.isAdmin();
    }

    private Map<String, Object> switchAdminMode(HttpServletRequest request, Map<String, Object> view) {
        long userId = Long.parseLong(request.getParameter("userId"));
        User correctedUser = userService.find(userId);
        if (!isAdmin(request, view) || userId == user.getId()) {
            view.put("success", false);
            view.put("message", "Not allowed operation");
            return view;
        }
        userService.switchAdminMode(userId, !correctedUser.isAdmin());
        if (userId == user.getId()) {
            view.put("message", "Not allowed operation");
        }
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
