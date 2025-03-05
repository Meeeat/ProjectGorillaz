package com.javarush.siberia.controller;

import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_AUTHOR_URL)
public class AuthorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAuthor(req, resp)) {
            return;
        }
        req.setAttribute(ATTR_MESSAGE, MESSAGE_AUTHOR_PANEL);
        req.getRequestDispatcher(JSP_INDEX).forward(req, resp);
    }
}