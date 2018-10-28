package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {

    protected UserService userService = new UserService();
    protected NewsRepository newsRepository = new NewsRepositoryImpl();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.userCount());
        view.put("newsList", newsRepository.findAll());
        view.put("userService", userService);
        if (request.getSession().getAttribute("user") != null) {
            view.put("username", ((User)request.getSession().getAttribute("user")).getLogin());
        }
    }

    protected void after() {

    }
}
