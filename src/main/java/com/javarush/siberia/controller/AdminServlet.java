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
import java.util.Collection;

@WebServlet(name="AdminServlet", urlPatterns="/admin")
public class AdminServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No access");
            return;
        }

        String editUsername = req.getParameter("editUsername");
        if (editUsername != null && !editUsername.isEmpty()) {
            User editUser = userService.login(editUsername, "no_password");
            editUser = userService.getUserRepository().findByUsername(editUsername);
            if (editUser == null) {
                req.setAttribute("error", "Can't find user");
            } else {
                req.setAttribute("editUser", editUser);
            }
        }

        Collection<User> allUsers = userService.getUserRepository().getAllUsers();
        req.setAttribute("users", allUsers);
        req.setAttribute("title", "Admin-panel");
        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User)req.getSession().getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No access");
            return;
        }

        String oldUsername = req.getParameter("oldUsername");
        String newUsername = req.getParameter("newUsername");
        String newPassword = req.getParameter("newPassword");
        String newRoleStr  = req.getParameter("newRole");
        Role newRole = null;
        if (newRoleStr != null && !newRoleStr.isEmpty()) {
            newRole = Role.valueOf(newRoleStr);
        }

        boolean success = userService.getUserRepository().updateUser(oldUsername, newUsername, newPassword, newRole);
        if (success) {
            req.setAttribute("message", "User update successful");
        } else {
            req.setAttribute("error", "Can't update user");
        }

        Collection<User> allUsers = userService.getUserRepository().getAllUsers();
        req.setAttribute("users", allUsers);
        req.setAttribute("title", "Admin-panel ");
        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
    }

}