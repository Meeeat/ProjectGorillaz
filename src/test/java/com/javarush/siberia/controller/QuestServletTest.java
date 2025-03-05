package com.javarush.siberia.controller;

import com.javarush.siberia.model.QuestState;
import com.javarush.siberia.model.QuestStep;
import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class QuestServletTest {

    private QuestServlet servlet;
    private QuestService questServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new QuestServlet();
        questServiceMock = mock(QuestService.class);
        Field field = QuestServlet.class.getDeclaredField("questService");
        field.setAccessible(true);
        field.set(servlet, questServiceMock);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    void doGet_NotLoggedIn_RedirectLogin() throws ServletException, IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(null);
        servlet.doGet(request, response);
        verify(response).sendRedirect("/login");
    }

    @Test
    void doGet_NoQuestState_NoQuestId_RedirectRoot() throws ServletException, IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(new User("u", "p", null));
        when(request.getParameter(AppConstants.PARAM_RESTART)).thenReturn(null);
        when(request.getParameter(AppConstants.PARAM_QUEST_ID)).thenReturn(null);
        servlet.doGet(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    void doGet_WithQuestState_ForwardQuestJSP() throws ServletException, IOException {
        User user = new User("testUser", "pass", null);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        QuestState qs = new QuestState("defaultQuest", "start");
        when(session.getAttribute("questState")).thenReturn(qs);

        Map<String, String> options = new HashMap<>();
        options.put("Go left", "left");
        options.put("Go right", "right");
        QuestStep mockStep = new QuestStep("Text", "img.jpg", options, false, false);

        when(questServiceMock.getStep("defaultQuest", "start")).thenReturn(mockStep);
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("step"), eq(mockStep));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_NotLoggedIn_RedirectLogin() throws ServletException, IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("/login");
    }

    @Test
    void doPost_NoQuestState_RedirectRoot() throws ServletException, IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(new User("u", "p", null));
        when(session.getAttribute("questState")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("/");
    }

    @Test
    void doPost_ChoiceOption1_EndVictory_ForwardResultJSP() throws ServletException, IOException {
        User user = new User("u", "p", null);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        QuestState qs = new QuestState("q1", "start");
        when(session.getAttribute("questState")).thenReturn(qs);

        Map<String, String> currentOptions = new HashMap<>();
        currentOptions.put("option1", "stepVictory");
        currentOptions.put("option2", "stepDefeat");
        QuestStep currentStep = new QuestStep("current", "img.jpg", currentOptions, false, false);
        when(questServiceMock.getStep("q1", "start")).thenReturn(currentStep);

        when(request.getParameter(AppConstants.PARAM_CHOICE)).thenReturn("option1");

        QuestStep nextStep = new QuestStep("You win!", "win.jpg", new HashMap<>(), true, true);
        when(questServiceMock.getStep("q1", "stepVictory")).thenReturn(nextStep);

        servlet.doPost(request, response);

        verify(questServiceMock).getStep("q1", "stepVictory");
        verify(request).setAttribute(eq("step"), eq(nextStep));
        verify(dispatcher).forward(request, response);
    }
}