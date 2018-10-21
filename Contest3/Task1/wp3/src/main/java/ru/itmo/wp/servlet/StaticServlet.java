package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] requestedFiles = uri.split("\\+");
        for (String currentFileName : requestedFiles) {
            if (!getFile(currentFileName).isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        response.setContentType(getContentTypeFromName(requestedFiles[0]));
        OutputStream responseOutputStream = response.getOutputStream();
        for (String currentFileName : requestedFiles) {
            File file = getFile(currentFileName);
            Files.copy(file.toPath(), responseOutputStream);
        }
        responseOutputStream.flush();
    }

    private File getFile(String uri) {
        if (uri.charAt(0) != '/') {
            uri = '/' + uri;
        }
        File returnFile = new File(new File(getServletContext().getRealPath("")).getParentFile().getParentFile().getAbsolutePath() + "/src/main/webapp/static" + uri);
        if (returnFile.isFile()) {
            return returnFile;
        } else {
            returnFile = new File(getServletContext().getRealPath("/static" + uri));
            return returnFile;
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
