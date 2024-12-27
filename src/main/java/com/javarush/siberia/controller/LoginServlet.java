package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(AppConstants.JSP_LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(AppConstants.PARAM_USERNAME);
        String password = req.getParameter(AppConstants.PARAM_PASSWORD);

        User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(AppConstants.SESSION_USER, user);
            resp.sendRedirect("/");
        } else {
            req.setAttribute(AppConstants.ATTR_ERROR_MESSAGE, AppConstants.ERROR_INVALID_CRED);
            req.getRequestDispatcher(AppConstants.JSP_LOGIN).forward(req, resp);
        }
    }

}