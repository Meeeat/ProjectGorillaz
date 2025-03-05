package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_LOGIN_URL)
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(PARAM_USERNAME);
        String password = req.getParameter(PARAM_PASSWORD);

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(SESSION_USER, user);
            resp.sendRedirect("/");
        } else {
            req.setAttribute(ATTR_ERROR_MESSAGE, ERROR_INVALID_CRED);
            req.getRequestDispatcher(JSP_LOGIN).forward(req, resp);
        }
    }

}