package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.EmailConfirmationService;
import ru.itmo.webmail.model.service.EventService;
import ru.itmo.webmail.model.service.TalkService;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    static final String USER_ID_SESSION_KEY = "userId";

    protected UserService userService = new UserService();
    protected EmailConfirmationService emailConfirmationService = new EmailConfirmationService();
    protected EventService eventService = new EventService();
    protected TalkService talkService = new TalkService();

    protected User user;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        Long userId = (Long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        if (userId != null) {
            user = userService.find(userId);
            view.put("user", user);
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }


    protected void setAuthorised(HttpServletRequest request, User user) {
        request.getSession(true).setAttribute(USER_ID_SESSION_KEY, user.getId());
        this.user = user;
        eventService.publishLogEvent(user.getId(), Event.EventType.ENTER);
    }

    protected void setUnauthorised(HttpServletRequest request) {
        eventService.publishLogEvent(user.getId(), Event.EventType.LOGOUT);
        request.getSession().removeAttribute(USER_ID_SESSION_KEY);
        user = null;
    }
}
