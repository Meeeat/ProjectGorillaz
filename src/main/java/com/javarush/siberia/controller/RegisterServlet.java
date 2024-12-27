package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(AppConstants.JSP_REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(AppConstants.PARAM_USERNAME);
        String password = req.getParameter(AppConstants.PARAM_PASSWORD);

        boolean success = userService.register(username, password, Role.USER);
        if (success) {
            User newUser = userService.login(username, password);
            if (newUser != null) {
                req.getSession().setAttribute(AppConstants.SESSION_USER, newUser);
            }
            resp.sendRedirect("/");
        } else {
            req.setAttribute(AppConstants.ATTR_ERROR_MESSAGE, AppConstants.ERROR_USER_EXISTS);
            req.getRequestDispatcher(AppConstants.JSP_REGISTER).forward(req, resp);
        }
    }

}