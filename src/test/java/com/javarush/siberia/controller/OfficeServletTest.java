package com.javarush.siberia.controller;

import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OfficeServletTest {

    private OfficeServlet servlet;
    private QuestService questServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new OfficeServlet();
        questServiceMock = mock(QuestService.class);
        try {
            var field = OfficeServlet.class.getDeclaredField("questService");
            field.setAccessible(true);
            field.set(servlet, questServiceMock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    void doGet_UserNotAuthorOrAdmin_Forbidden() throws ServletException, IOException {
        User user = new User("test", "test", Role.USER);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        servlet.doGet(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), eq(AppConstants.NO_ACCESS));
    }

    @Test
    void doGet_AuthorOk_Forward() throws ServletException, IOException {
        User author = new User("author", "author", Role.AUTHOR);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(author);
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_CreateQuest() throws ServletException, IOException {
        User admin = new User("admin", "admin", Role.ADMIN);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(admin);
        when(request.getParameter(AppConstants.PARAM_ACTION)).thenReturn(AppConstants.ACTION_CREATE_QUEST);
        when(request.getParameter(AppConstants.PARAM_QUEST_ID)).thenReturn("myQuest");
        servlet.doPost(request, response);
        verify(questServiceMock).addQuest("myQuest");
        verify(request).setAttribute(eq(AppConstants.ATTR_MESSAGE), eq("Quest myQuest created!"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_AddStep() throws ServletException, IOException {
        User admin = new User("admin", "admin", Role.ADMIN);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(admin);
        when(request.getParameter(AppConstants.PARAM_ACTION)).thenReturn(AppConstants.ACTION_ADD_STEP);
        when(request.getParameter(AppConstants.PARAM_QUEST_ID)).thenReturn("q1");
        when(request.getParameter(AppConstants.PARAM_STEP_ID)).thenReturn("s1");
        when(request.getParameter(AppConstants.PARAM_TEXT)).thenReturn("text");
        when(request.getParameter(AppConstants.PARAM_IMAGE_PATH)).thenReturn("images/step1.jpg");
        when(request.getParameter(AppConstants.PARAM_OPTION1)).thenReturn("Go");
        when(request.getParameter(AppConstants.PARAM_OPTION2)).thenReturn("Stay");
        when(request.getParameter(AppConstants.PARAM_NEXT1)).thenReturn("nextS1");
        when(request.getParameter(AppConstants.PARAM_NEXT2)).thenReturn("nextS2");
        when(request.getParameter(AppConstants.PARAM_END)).thenReturn("on");
        when(request.getParameter(AppConstants.PARAM_VICTORY)).thenReturn("off");
        servlet.doPost(request, response);
        verify(questServiceMock).addStep(
                eq("q1"),
                eq("s1"),
                eq("text"),
                eq("images/step1.jpg"),
                eq("Go"),
                eq("Stay"),
                eq("nextS1"),
                eq("nextS2"),
                eq(true),
                eq(false)
        );
        verify(request).setAttribute(eq(AppConstants.ATTR_MESSAGE),
                eq(AppConstants.ATTR_STEP_MSG + "s1" + AppConstants.ATTR_ADD_TO_QUEST_MSG + "q1"));
        verify(dispatcher).forward(request, response);
    }

}