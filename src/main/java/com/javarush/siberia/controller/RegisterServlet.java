package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_REGISTER_URL)
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(PARAM_USERNAME);
        String password = req.getParameter(PARAM_PASSWORD);

        boolean success = userService.register(username, password, Role.USER);
        if (success) {
            User newUser = userService.login(username, password);
            if (newUser != null) {
                req.getSession().setAttribute(SESSION_USER, newUser);
            }
            resp.sendRedirect("/");
        } else {
            req.setAttribute(ATTR_ERROR_MESSAGE, ERROR_USER_EXISTS);
            req.getRequestDispatcher(JSP_REGISTER).forward(req, resp);
        }
    }

}