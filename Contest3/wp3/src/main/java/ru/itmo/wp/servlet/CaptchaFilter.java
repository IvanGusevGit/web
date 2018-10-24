package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isGetRequest(request) || isCaptchaPassed(request) || isCaptchaSuccess(request)) {
            chain.doFilter(request, response);
        } else {
            String captchaText = getCaptchaText();
            request.getSession().setAttribute("expected-captcha-input", captchaText);
            response.setContentType("text/html");
            String imageSource = new StringBuilder("<img src=\"data:image/jpeg;base64,").append(Base64.getEncoder().encodeToString(ImageUtils.toPng(captchaText))).append("\">").toString();
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.print(imageSource);
            Files.copy(Paths.get(getServletContext().getRealPath("static"), "captcha_page.html"), outputStream);
            outputStream.flush();
        }
    }

    private String getCaptchaText(){
        int number = 999 - new Random(System.currentTimeMillis()).nextInt(900);
        return Integer.toString(number);
    }

    private boolean isGetRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("get");
    }

    private boolean isCaptchaPassed(HttpServletRequest request) {
        Boolean captchaPassed = (Boolean) request.getSession().getAttribute("captcha-passed");
        return captchaPassed != null && captchaPassed;
    }

    private boolean isCaptchaSuccess(HttpServletRequest request) {
        String captchaInput = request.getParameter("captcha-input");
        String expectedCaptchaInput = (String) request.getSession().getAttribute("expected-captcha-input");
        if (captchaInput != null && expectedCaptchaInput != null) {
            if (captchaInput.equals(expectedCaptchaInput)) {
                request.getSession().setAttribute("captcha-passed", true);
                return true;
            }
        }
        return false;
    }
}
