package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TalksPage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (user == null) {
            throw new RedirectException("/index");
        }
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        updateView(view);
    }

    public void submitTalk(HttpServletRequest request, Map<String, Object> view) {
        Talk talk = new Talk();
        talk.setSourceUserId(user.getId());
        talk.setTargetUserId(Long.parseLong(request.getParameter("targetSelect")));
        String text = new String(request.getParameter("messageText").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        talk.setText(text);
        talkService.saveTalk(talk);
        updateView(view);
    }

    private void updateView(Map<String, Object> view) {
        view.put("messages", talkService.makeMessageList(user));
        view.put("users", userService.findAll());
    }
}
