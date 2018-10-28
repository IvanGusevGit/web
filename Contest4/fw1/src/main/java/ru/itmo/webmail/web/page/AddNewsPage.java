package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

public class AddNewsPage extends Page {
    public void action(HttpServletRequest request) throws RedirectException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new RedirectException("/index", "askAuthorize");
        }
    }
    public void submitNews(HttpServletRequest request) {
        NewsRepository newsRepository = new NewsRepositoryImpl();
        newsRepository.save(new News(((User) request.getSession().getAttribute("user")).getId(), request.getParameter("newsText")));
        throw new RedirectException("/index", "newsAddDone");
    }
}
