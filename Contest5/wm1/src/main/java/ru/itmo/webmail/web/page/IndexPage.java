package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered. Verification letter is send to your email address.");
    }

    private void invalidConfirmationLink(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "Invalid confirmation link");
    }

    private void confirmSuccess(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "Successful confirmation");
    }
}
