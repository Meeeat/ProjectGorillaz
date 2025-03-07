package com.javarush.siberia.controller;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.SecurityUtil;
import com.javarush.siberia.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_QUEST_URL)
public class QuestServlet extends HttpServlet {

    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkLoggedIn(req, resp)) {
            return;
        }

        HttpSession session = req.getSession();
        String restart = req.getParameter(PARAM_RESTART);
        if ("true".equals(restart)) {
            SessionUtil.resetQuestState(session);
        }

        QuestState state = SessionUtil.getQuestState(session);
        String questId = req.getParameter(PARAM_QUEST_ID);

        if (state == null && questId != null && !questId.isEmpty()) {
            SessionUtil.startQuest(session, questId);
            state = SessionUtil.getQuestState(session);
        }

        if (state == null) {
            resp.sendRedirect("/");
            return;
        }

        QuestStep step = questService.getStep(state.getQuestId(), state.getCurrentStepId());
        if (step == null) {
            SessionUtil.resetQuestState(session);
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute(ATTR_STEP, step);
        req.setAttribute(ATTR_TITLE, ATTR_QUEST_TITLE);
        req.getRequestDispatcher(JSP_QUEST).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkLoggedIn(req, resp)) {
            return;
        }

        HttpSession session = req.getSession();
        QuestState state = SessionUtil.getQuestState(session);
        if (state == null) {
            resp.sendRedirect("/");
            return;
        }

        QuestStep currentStep = questService.getStep(state.getQuestId(), state.getCurrentStepId());
        if (currentStep == null) {
            SessionUtil.resetQuestState(session);
            resp.sendRedirect("/");
            return;
        }

        String choice = req.getParameter(PARAM_CHOICE);
        String nextStepId = currentStep.getOptions().get(choice);

        if (nextStepId != null) {
            QuestStep nextStep = questService.getStep(state.getQuestId(), nextStepId);
            if (nextStep != null) {
                state.setCurrentStepId(nextStepId);
                if (nextStep.isEnd()) {
                    boolean victory = nextStep.isVictory();
                    User user = (User) session.getAttribute(SESSION_USER);
                    SessionUtil.incrementStats(user.getId(), victory);
                    req.setAttribute(ATTR_STEP, nextStep);
                    req.setAttribute(ATTR_TITLE, ATTR_RESULT_TITLE);
                    req.getRequestDispatcher(JSP_RESULT).forward(req, resp);
                } else {
                    resp.sendRedirect(WS_QUEST_URL);
                }
            } else {
                SessionUtil.resetQuestState(session);
                resp.sendRedirect("/");
            }
        } else {
            req.setAttribute(ATTR_STEP, currentStep);
            req.setAttribute(ATTR_ERROR, "Неверный выбор");
            req.getRequestDispatcher(JSP_QUEST).forward(req, resp);
        }
    }
}