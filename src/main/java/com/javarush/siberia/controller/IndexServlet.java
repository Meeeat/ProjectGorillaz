package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(urlPatterns={"/", WS_INDEX_URL})
public class IndexServlet extends HttpServlet {

    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> quests = questService.getAllQuestIds();
        User user = (User) req.getSession().getAttribute(SESSION_USER);

        boolean loggedIn = (user != null);
        req.setAttribute(ATTR_LOGGED_IN, loggedIn);
        req.setAttribute(ATTR_QUESTS, quests);
        req.setAttribute(ATTR_TITLE, ATTR_MAIN_TITLE);
        req.getRequestDispatcher(JSP_INDEX).forward(req, resp);
    }

}