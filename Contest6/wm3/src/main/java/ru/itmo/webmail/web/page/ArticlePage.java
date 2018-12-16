package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (user == null) {
            throw new RedirectException("/index");
        }
    }

    private Map<String, Object> submitArticle(HttpServletRequest request, Map<String, Object> view) {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("content"));
        article.setUserId((Long)request.getSession().getAttribute(USER_ID_SESSION_KEY));

        try {
            articleService.validate(article);
            articleService.save(article);
            view.put("success", true);
        } catch (ValidationException e) {
            view.put("title", article.getTitle());
            view.put("content", article.getText());
            view.put("error", e.getMessage());
            view.put("success", false);
        }

        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        //nothing
    }
}
