package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MyarticlesPage extends Page {

    private void action(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (user == null) {
            throw new RedirectException("/index");
        }
    }

    private List<Article> find(HttpServletRequest request, Map<String, Object> view) {
        List<Article> answer = articleService.findByUserId((Long)request.getSession().getAttribute(USER_ID_SESSION_KEY));
        return answer;
    }

    private Map<String, Object> setVisibility(HttpServletRequest request, Map<String, Object> view) {
        Article article = articleService.find(Long.parseLong(request.getParameter("articleId")));
        if (article.getUserId() != (Long)request.getSession().getAttribute(USER_ID_SESSION_KEY)) {
            view.put("success", false);
        } else {
            articleService.setHidden(article.getId(), !article.isHidden());
            view.put("success", true);
        }
        return view;
    }

}
