package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class MessagesServlet extends HttpServlet {

    private Gson json_converter = new Gson();

    private List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String[] uri_piecies = request.getRequestURI().split("/");
        String process_result = null;
        response.setContentType("application/json");
        if (uri_piecies.length != 3) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            String demand = uri_piecies[2];
            if ("auth".equals(demand)) {
                process_result = processAuthRequest(request);
            } else if ("findAll".equals(demand)) {
                process_result = processFindAllRequest();
            } else if ("add".equals(demand)) {
                process_result = processAddRequest(request);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        PrintWriter writer = response.getWriter();
        writer.print(process_result);
        writer.flush();
    }

    private String processAuthRequest(HttpServletRequest request) {
        String user = (String) request.getParameter("user");
        if (user == null) {
            return json_converter.toJson("");
        } else {
            request.getSession().setAttribute("user", user);
            return json_converter.toJson(user);
        }
    }

    private String processFindAllRequest() {
        return json_converter.toJson(messages);
    }

    private String processAddRequest(HttpServletRequest request) {
        String user = (String) request.getSession().getAttribute("user");
        String text = request.getParameter("text");
        if (text != null && !text.equals("")) {
            messages.add(new Message(user, text));
        }
        return json_converter.toJson("");
    }

    private class Message {

        public String user, text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
