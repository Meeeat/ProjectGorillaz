package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="OfficeServlet", urlPatterns="/office")
public class OfficeServlet extends HttpServlet {
    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null || (user.getRole() != Role.AUTHOR && user.getRole() != Role.ADMIN)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No access");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/office.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null || (user.getRole() != Role.AUTHOR && user.getRole() != Role.ADMIN)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No access");
            return;
        }

        String action = req.getParameter("action");
        if ("createQuest".equals(action)) {
            String questId = req.getParameter("questId");
            questService.addQuest(questId);
            req.setAttribute("message", "Quest " + questId + " created!");
        } else if ("addStep".equals(action)) {
            String questId = req.getParameter("questId");
            String stepId = req.getParameter("stepId");
            String text = req.getParameter("text");
            String imagePath = req.getParameter("imagePath");
            String option1 = req.getParameter("option1");
            String option2 = req.getParameter("option2");
            String next1 = req.getParameter("next1");
            String next2 = req.getParameter("next2");
            boolean isEnd = "on".equals(req.getParameter("end"));
            boolean isVictory = "on".equals(req.getParameter("victory"));

            questService.addStep(questId, stepId, text, imagePath, option1, option2, next1, next2, isEnd, isVictory);
            req.setAttribute("message", "Step " + stepId + " added to quest " + questId);
        }
        req.getRequestDispatcher("/WEB-INF/office.jsp").forward(req, resp);
    }

}