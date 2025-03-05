package com.javarush.siberia.controller;

import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
            req.setAttribute(ATTR_MESSAGE, ATTR_QUEST_MSG + questId + ATTR_CREATED_MSG);
        } else if (ACTION_ADD_STEP.equals(action)) {
            String questId = req.getParameter(PARAM_QUEST_ID);
            String stepId = req.getParameter(PARAM_STEP_ID);
            String text = req.getParameter(PARAM_TEXT);
            String imagePath = req.getParameter(PARAM_IMAGE_PATH);

            Map<String, String> options = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                if (paramName.startsWith(PARAM_OPTION)) {
                    String optionText = req.getParameter(paramName);
                    String nextStepId = req.getParameter(PARAM_NEXT + paramName.substring(PARAM_OPTION_INDEX));
                    options.put(optionText, nextStepId);
                }
            }

            boolean isEnd = PARAM_CHECKBOX_ON.equals(req.getParameter(PARAM_END));
            boolean isVictory = PARAM_CHECKBOX_ON.equals(req.getParameter(PARAM_VICTORY));

            questService.addStep(questId, stepId, text, imagePath, options, isEnd, isVictory);
            req.setAttribute(ATTR_MESSAGE, ATTR_STEP_MSG + stepId + ATTR_ADD_TO_QUEST_MSG + questId);
        }
        req.getRequestDispatcher(JSP_OFFICE).forward(req, resp);
    }

}