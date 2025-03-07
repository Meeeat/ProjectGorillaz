package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_ADMIN_URL)
public class AdminServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdmin(req, resp)) {
            return;
        }

        String editUsername = req.getParameter(PARAM_EDIT_USERNAME);
        if (editUsername != null && !editUsername.isEmpty()) {
            User editUser = userService.login(editUsername, null);
            if (editUser == null) {
                req.setAttribute(ATTR_ERROR, ERROR_CANT_FIND_USER);
            } else {
                req.setAttribute(ATTR_EDIT_USER, editUser);
            }
        }

        Collection<User> allUsers = userService.getAllUsers();
        req.setAttribute(ATTR_USERS, allUsers);

        req.setAttribute(ATTR_TITLE, ATTR_ADMIN_PANEL);
        req.getRequestDispatcher(JSP_ADMIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdmin(req, resp)) {
            return;
        }

        String oldUsername = req.getParameter(PARAM_OLD_USERNAME);
        String newUsername = req.getParameter(PARAM_NEW_USERNAME);
        String newPassword = req.getParameter(PARAM_NEW_PASSWORD);
        String newRoleStr  = req.getParameter(PARAM_NEW_ROLE);

        Role newRole = null;
        if (newRoleStr != null && !newRoleStr.isEmpty()) {
            newRole = Role.valueOf(newRoleStr);
        }

        boolean success = userService.updateUser(oldUsername, newUsername, newPassword, newRole);
        if (success) {
            req.setAttribute(ATTR_MESSAGE, SUCCESS_USER_UPDATE);
        } else {
            req.setAttribute(ATTR_ERROR, ERROR_CANT_UPDATE_USER);
        }

        Collection<User> allUsers = userService.getAllUsers();
        req.setAttribute(ATTR_USERS, allUsers);
        req.setAttribute(ATTR_TITLE, ATTR_ADMIN_PANEL);
        req.getRequestDispatcher(JSP_ADMIN).forward(req, resp);
    }
}