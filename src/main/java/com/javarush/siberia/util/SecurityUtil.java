package com.javarush.siberia.util;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class SecurityUtil {
    private SecurityUtil() {}

    public static boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(AppConstants.SESSION_USER);
        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, AppConstants.NO_ACCESS);
            return false;
        }
        return true;
    }

    public static boolean checkAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(AppConstants.SESSION_USER);
        if (user == null || user.getRole() != Role.AUTHOR) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, AppConstants.NO_ACCESS);
            return false;
        }
        return true;
    }

    public static boolean checkAdminOrAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(AppConstants.SESSION_USER);
        if (user == null || (user.getRole() != Role.ADMIN && user.getRole() != Role.AUTHOR)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, AppConstants.NO_ACCESS);
            return false;
        }
        return true;
    }

    public static boolean checkLoggedIn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(AppConstants.SESSION_USER);
        if (user == null) {
            resp.sendRedirect("/login");
            return false;
        }
        return true;
    }

}