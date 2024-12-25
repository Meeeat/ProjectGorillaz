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

@WebServlet(name="IndexServlet", urlPatterns={"/"})
public class IndexServlet extends HttpServlet {
    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> quests = questService.getAllQuestIds();
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("loggedIn", user != null);
        req.setAttribute("quests", quests);
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }

}