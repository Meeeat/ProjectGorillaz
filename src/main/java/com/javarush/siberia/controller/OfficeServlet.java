package com.javarush.siberia.controller;

import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.AppConstants;
import com.javarush.siberia.util.SecurityUtil;
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
        if (!SecurityUtil.checkAdminOrAuthor(req, resp)) {
            return;
        }
        req.getRequestDispatcher(AppConstants.JSP_OFFICE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdminOrAuthor(req, resp)) {
            return;
        }

        String action = req.getParameter(AppConstants.PARAM_ACTION);
        if (AppConstants.ACTION_CREATE_QUEST.equals(action)) {
            String questId = req.getParameter(AppConstants.PARAM_QUEST_ID);
            questService.addQuest(questId);
            req.setAttribute(AppConstants.ATTR_MESSAGE,
                    AppConstants.ATTR_QUEST_MSG + questId + AppConstants.ATTR_CREATED_MSG);
        } else if (AppConstants.ACTION_ADD_STEP.equals(action)) {
            String questId = req.getParameter(AppConstants.PARAM_QUEST_ID);
            String stepId  = req.getParameter(AppConstants.PARAM_STEP_ID);
            String text    = req.getParameter(AppConstants.PARAM_TEXT);
            String imagePath = req.getParameter(AppConstants.PARAM_IMAGE_PATH);
            String option1 = req.getParameter(AppConstants.PARAM_OPTION1);
            String option2 = req.getParameter(AppConstants.PARAM_OPTION2);
            String next1   = req.getParameter(AppConstants.PARAM_NEXT1);
            String next2   = req.getParameter(AppConstants.PARAM_NEXT2);

            boolean isEnd     = "on".equals(req.getParameter(AppConstants.PARAM_END));
            boolean isVictory = "on".equals(req.getParameter(AppConstants.PARAM_VICTORY));

            questService.addStep(
                    questId, stepId, text, imagePath,
                    option1, option2, next1, next2,
                    isEnd, isVictory
            );
            req.setAttribute(AppConstants.ATTR_MESSAGE,
                    AppConstants.ATTR_STEP_MSG + stepId + AppConstants.ATTR_ADD_TO_QUEST_MSG + questId);
        }
        req.getRequestDispatcher(AppConstants.JSP_OFFICE).forward(req, resp);
    }

}