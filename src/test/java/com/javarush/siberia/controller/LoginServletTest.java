package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.lang.reflect.Field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LoginServletTest {

    private LoginServlet servlet;
    private UserService userServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new LoginServlet();

        userServiceMock = Mockito.mock(UserService.class);

        Field serviceField = LoginServlet.class.getDeclaredField("userService");
        serviceField.setAccessible(true);
        serviceField.set(servlet, userServiceMock);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/login.jsp")).thenReturn(dispatcher);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/WEB-INF/login.jsp");
        verify(dispatcher).forward(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    void testDoPost_ValidLogin() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("validUser");
        when(request.getParameter("password")).thenReturn("123");

        User mockUser = new User("validUser", "123", null);
        when(userServiceMock.login("validUser", "123")).thenReturn(mockUser);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("user"), eq(mockUser));
        verify(response).sendRedirect("index");
        verify(dispatcher, never()).forward(any(), any());
    }

    @Test
    void testDoPost_InvalidLogin() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("badUser");
        when(request.getParameter("password")).thenReturn("wrong");

        when(userServiceMock.login("badUser", "wrong")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), anyString());
        verify(request).getRequestDispatcher("/WEB-INF/login.jsp");
        verify(dispatcher).forward(request, response);

        verify(response, never()).sendRedirect(anyString());
    }

}