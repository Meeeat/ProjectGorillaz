package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        req.setAttribute(ATTR_TITLE, ATTR_OFFICE_TITLE);
        req.getRequestDispatcher(JSP_OFFICE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SecurityUtil.checkAdminOrAuthor(req, resp)) {
            return;
        }
        String action = req.getParameter(PARAM_ACTION);
        User user = (User) req.getSession().getAttribute(SESSION_USER);
        try {
            if (ACTION_CREATE_QUEST.equals(action)) {
                String questId = req.getParameter(PARAM_QUEST_ID);
                questService.addQuest(questId, user);
                req.setAttribute(ATTR_MESSAGE, ATTR_QUEST_MSG + questId + ATTR_CREATED_MSG);
            } else if (ACTION_ADD_STEP.equals(action)) {
                String questId = req.getParameter(PARAM_QUEST_ID);
                String stepId = req.getParameter(PARAM_STEP_ID);
                String text = req.getParameter(PARAM_TEXT);
                String imagePath = req.getParameter(PARAM_IMAGE_PATH);
                Map<String, String> options = new HashMap<>();
                int i = 1;
                while (true) {
                    String option = req.getParameter(PARAM_OPTION + i);
                    String next = req.getParameter(PARAM_NEXT + i);
                    if (option == null || next == null) break;
                    options.put(option, next);
                    i++;
                }
                boolean isEnd = PARAM_CHECKBOX_ON.equals(req.getParameter(PARAM_END));
                boolean isVictory = PARAM_CHECKBOX_ON.equals(req.getParameter(PARAM_VICTORY));
                questService.addStep(questId, stepId, text, imagePath, options, isEnd, isVictory);
                req.setAttribute(ATTR_MESSAGE, ATTR_STEP_MSG + stepId + ATTR_ADD_TO_QUEST_MSG + questId);
            } else {
                req.setAttribute(ATTR_ERROR, ERROR_UNKNOWN_ACTION + action);
            }
        } catch (Exception e) {
            req.setAttribute(ATTR_ERROR, ERROR_OPERATION_FAILED + e.getMessage());
        }
        req.getRequestDispatcher(JSP_OFFICE).forward(req, resp);
    }
}