package com.javarush.siberia.controller;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name="QuestServlet", urlPatterns="/quest")
public class QuestServlet extends HttpServlet {

    private final QuestService questService = new QuestService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        HttpSession session = req.getSession();
        String restart = req.getParameter("restart");
        if ("true".equals(restart)) {
            SessionUtil.resetQuestState(session);
        }

        QuestState state = SessionUtil.getQuestState(session);
        String questId = req.getParameter("questId");

        if (state == null && questId != null && !questId.isEmpty()) {
            SessionUtil.startQuest(session, questId);
            state = SessionUtil.getQuestState(session);
        }

        if (state == null) {
            resp.sendRedirect("/");
            return;
        }

        QuestStep step = questService.getStep(state.getQuestId(), state.getCurrentStepId());
        req.setAttribute("step", step);
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        HttpSession session = req.getSession();
        QuestState state = SessionUtil.getQuestState(session);
        if (state == null) {
            resp.sendRedirect("/");
            return;
        }

        QuestStep currentStep = questService.getStep(state.getQuestId(), state.getCurrentStepId());
        String choice = req.getParameter("choice");
        String nextStepId = null;
        if ("option1".equals(choice)) {
            nextStepId = currentStep.getNextStepIfOption1();
        } else if ("option2".equals(choice)) {
            nextStepId = currentStep.getNextStepIfOption2();
        }

        if (nextStepId != null) {
            QuestStep nextStep = questService.getStep(state.getQuestId(), nextStepId);
            state.setCurrentStepId(nextStepId);
            if (nextStep.isEnd()) {
                boolean victory = nextStep.isVictory();
                SessionUtil.incrementStats(user.getUsername(), victory);
                req.setAttribute("step", nextStep);
                req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
                return;
            }
        }
        resp.sendRedirect("/quest");
    }

}