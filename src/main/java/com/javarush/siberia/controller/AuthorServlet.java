package com.javarush.siberia.controller;

import com.javarush.siberia.util.AppConstants;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="AuthorServlet", urlPatterns="/author")
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAuthor(req, resp)) {
            return;
        }
        req.setAttribute(AppConstants.ATTR_MESSAGE, AppConstants.MESSAGE_AUTHOR_PANEL);
        req.getRequestDispatcher(AppConstants.JSP_INDEX).forward(req, resp);
    }
}