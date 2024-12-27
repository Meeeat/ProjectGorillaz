package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.List;

class IndexServletTest {

    private IndexServlet servlet;
    private QuestService questServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setup() {
        servlet = new IndexServlet();
        questServiceMock = mock(QuestService.class);
        try {
            var field = IndexServlet.class.getDeclaredField("questService");
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
        when(request.getRequestDispatcher(AppConstants.JSP_INDEX)).thenReturn(dispatcher);
        when(questServiceMock.getAllQuestIds()).thenReturn(List.of("defaultQuest", "myQuest"));
    }

    @Test
    void doGet_NotLoggedIn() throws ServletException, IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(null);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("loggedIn"), eq(false));
        verify(request).setAttribute(eq("quests"), any());
        verify(request).setAttribute(eq(AppConstants.ATTR_TITLE), eq("Главная"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGet_LoggedIn() throws ServletException, IOException {
        User user = new User("someUser", "pass", null);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("loggedIn"), eq(true));
        verify(request).setAttribute(eq("quests"), any());
        verify(dispatcher).forward(request, response);
    }

}