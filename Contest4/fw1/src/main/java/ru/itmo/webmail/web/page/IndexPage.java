package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {

    protected void action() {
        // No operations.
    }

    private void registrationDone(Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void enterDone(Map<String, Object> view) {
        view.put("message", "You've successfully entered.");
    }

    private void logoutDone(Map<String, Object> view) {
        view.put("message", "You've successfully logged out");
    }

    private void askAuthorize(Map<String, Object> view) {
        view.put("message", "You should be authorized to do this action,");
    }

    private void newsAddDone(Map<String, Object> view) {
        view.put("message", "Your news was published.");
    }
}
