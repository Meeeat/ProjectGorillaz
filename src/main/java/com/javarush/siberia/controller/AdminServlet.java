package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import com.javarush.siberia.util.AppConstants;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name="AdminServlet", urlPatterns="/admin")
public class AdminServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdmin(req, resp)) {
            return;
        }

        String editUsername = req.getParameter(AppConstants.PARAM_EDIT_USERNAME);
        if (editUsername != null && !editUsername.isEmpty()) {
            User editUser = userService.getUserRepository().findByUsername(editUsername);
            if (editUser == null) {
                req.setAttribute(AppConstants.ATTR_ERROR, AppConstants.ERROR_CANT_FIND_USER);
            } else {
                req.setAttribute(AppConstants.ATTR_EDIT_USER, editUser);
            }
        }

        Collection<User> allUsers = userService.getUserRepository().getAllUsers();
        req.setAttribute(AppConstants.ATTR_USERS, allUsers);

        req.setAttribute(AppConstants.ATTR_TITLE, AppConstants.ATTR_ADMIN_PANEL);
        req.getRequestDispatcher(AppConstants.JSP_ADMIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!SecurityUtil.checkAdmin(req, resp)) {
            return;
        }

        String oldUsername = req.getParameter(AppConstants.PARAM_OLD_USERNAME);
        String newUsername = req.getParameter(AppConstants.PARAM_NEW_USERNAME);
        String newPassword = req.getParameter(AppConstants.PARAM_NEW_PASSWORD);
        String newRoleStr  = req.getParameter(AppConstants.PARAM_NEW_ROLE);

        Role newRole = null;
        if (newRoleStr != null && !newRoleStr.isEmpty()) {
            newRole = Role.valueOf(newRoleStr);
        }

        boolean success = userService.getUserRepository().updateUser(oldUsername, newUsername, newPassword, newRole);
        if (success) {
            req.setAttribute(AppConstants.ATTR_MESSAGE, AppConstants.SUCCESS_USER_UPDATE);
        } else {
            req.setAttribute(AppConstants.ATTR_ERROR, AppConstants.ERROR_CANT_UPDATE_USER);
        }

        Collection<User> allUsers = userService.getUserRepository().getAllUsers();
        req.setAttribute(AppConstants.ATTR_USERS, allUsers);
        req.setAttribute(AppConstants.ATTR_TITLE, AppConstants.ATTR_ADMIN_PANEL);
        req.getRequestDispatcher(AppConstants.JSP_ADMIN).forward(req, resp);
    }

}