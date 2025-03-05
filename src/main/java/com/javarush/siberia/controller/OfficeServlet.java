package com.javarush.siberia.controller;

import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_OFFICE_URL)
public class OfficeServlet extends HttpServlet {

    private final QuestService questService = new QuestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdminOrAuthor(req, resp)) {
            return;
        }
        req.getRequestDispatcher(JSP_OFFICE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdminOrAuthor(req, resp)) {
            return;
        }

        String action = req.getParameter(PARAM_ACTION);
        if (ACTION_CREATE_QUEST.equals(action)) {
            String questId = req.getParameter(PARAM_QUEST_ID);
            questService.addQuest(questId);
            req.setAttribute(ATTR_MESSAGE,
                    ATTR_QUEST_MSG + questId + ATTR_CREATED_MSG);
        } else if (ACTION_ADD_STEP.equals(action)) {
            String questId = req.getParameter(PARAM_QUEST_ID);
            String stepId  = req.getParameter(PARAM_STEP_ID);
            String text    = req.getParameter(PARAM_TEXT);
            String imagePath = req.getParameter(PARAM_IMAGE_PATH);
            String option1 = req.getParameter(PARAM_OPTION1);
            String option2 = req.getParameter(PARAM_OPTION2);
            String next1   = req.getParameter(PARAM_NEXT1);
            String next2   = req.getParameter(PARAM_NEXT2);

            boolean isEnd     = "on".equals(req.getParameter(PARAM_END));
            boolean isVictory = "on".equals(req.getParameter(PARAM_VICTORY));

            questService.addStep(
                    questId, stepId, text, imagePath,
                    option1, option2, next1, next2,
                    isEnd, isVictory
            );
            req.setAttribute(ATTR_MESSAGE,
                    ATTR_STEP_MSG + stepId + ATTR_ADD_TO_QUEST_MSG + questId);
        }
        req.getRequestDispatcher(JSP_OFFICE).forward(req, resp);
    }

}