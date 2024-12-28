package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name="IndexServlet", urlPatterns={"/", "/index"})
public class IndexServlet extends HttpServlet {

    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> quests = questService.getAllQuestIds();
        User user = (User) req.getSession().getAttribute(AppConstants.SESSION_USER);

        boolean loggedIn = (user != null);
        req.setAttribute(AppConstants.ATTR_LOGGED_IN, loggedIn);
        req.setAttribute(AppConstants.ATTR_QUESTS, quests);
        req.setAttribute(AppConstants.ATTR_TITLE, AppConstants.ATTR_MAIN_TITLE);
        req.getRequestDispatcher(AppConstants.JSP_INDEX).forward(req, resp);
    }

}