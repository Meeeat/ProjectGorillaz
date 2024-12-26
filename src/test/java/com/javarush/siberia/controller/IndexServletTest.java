package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

public class IndexServletTest extends Mockito {

    private IndexServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private QuestService questServiceMock;

    @BeforeEach
    void setup() {
        servlet = new IndexServlet();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        questServiceMock = mock(QuestService.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(questServiceMock.getAllQuestIds()).thenReturn(List.of("defaultQuest", "myQuest"));
    }

    @Test
    void testDoGet_NotLoggedIn() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
        verify(request, times(1)).setAttribute(eq("loggedIn"), eq(false));
    }

    @Test
    void testDoGet_LoggedIn() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(new User("admin","admin",null));

        servlet.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
        verify(request, times(1)).setAttribute(eq("loggedIn"), eq(true));
    }
}