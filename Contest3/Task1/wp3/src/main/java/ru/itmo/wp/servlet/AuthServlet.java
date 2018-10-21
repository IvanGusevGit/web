package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthServlet extends HttpServlet {


    private String currentUser = "unknown";
    private Set<String> authorizedUsers = new HashSet<>();
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String uri = request.getRequestURI();
        if ("/message/auth".equals(uri)) {
            String user = request.getParameter("user");
            currentUser = user;
            if (user == null) return;
            if (authorizedUsers.contains(user)) {
                writeResponse(response, "");
            } else {
                authorizedUsers.add(user);
                writeResponse(response, user);
            }
        } else if ("/message/findAll".equals(uri)) {
            StringBuilder result = new StringBuilder("[");
            for (Message currentPair : messages) {
                result.append("{\"user\":\"").append(currentPair.user).append("\",\"text\":\"").append(currentPair.text).append("\"},");
            }
            if (result.charAt(result.length() - 1) == ',') {
                result.delete(result.length() - 1, result.length());
            }
            result.append(']');
            writeResponse(response, result.toString());
        } else if ("/message/add".equals(uri)) {
            messages.add(new Message(currentUser, request.getParameter("text")));
        }
        System.out.println(request.getRequestURI() + request.getParameter("user"));

    }

    private class Message {
        public String user, text;
        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private void writeResponse(HttpServletResponse response, String text) throws IOException{
        try {
            if (text == null) {
                return;
            }
            response.setContentType("application/json");
            String json = new Gson().toJson("abc");
            response.getWriter().print(json);
            response.getWriter().flush();
        } catch (NoClassDefFoundError e) {
            System.out.println("TROUBLE:::" + text);
        }
    }

}
