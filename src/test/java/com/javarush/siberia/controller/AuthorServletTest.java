package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.mockito.Mockito.*;

class AuthorServletTest {

    private AuthorServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new AuthorServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    void doGet_NotAuthor_Forbidden() throws ServletException, IOException {
        User user = new User("someUser", "123", Role.USER);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        servlet.doGet(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), eq(AppConstants.NO_ACCESS));
        verify(dispatcher, never()).forward(request, response);
    }

    @Test
    void doGet_AuthorOk_Forward() throws ServletException, IOException {
        User author = new User("author", "author", Role.AUTHOR);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(author);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq(AppConstants.ATTR_MESSAGE), eq(AppConstants.MESSAGE_AUTHOR_PANEL));
        verify(dispatcher).forward(request, response);
    }

}