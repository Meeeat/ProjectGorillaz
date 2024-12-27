package com.javarush.siberia.controller;

import com.javarush.siberia.model.User;
import com.javarush.siberia.service.UserService;
import com.javarush.siberia.util.AppConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LoginServletTest {

    private LoginServlet servlet;
    private UserService userServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new LoginServlet();
        userServiceMock = mock(UserService.class);
        Field serviceField = LoginServlet.class.getDeclaredField("userService");
        serviceField.setAccessible(true);
        serviceField.set(servlet, userServiceMock);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(AppConstants.JSP_LOGIN)).thenReturn(dispatcher);
    }

    @Test
    void doGet_ForwardToLogin() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(AppConstants.JSP_LOGIN);
        verify(dispatcher).forward(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    void doPost_ValidLogin() throws ServletException, IOException {
        when(request.getParameter(AppConstants.PARAM_USERNAME)).thenReturn("validUser");
        when(request.getParameter(AppConstants.PARAM_PASSWORD)).thenReturn("123");
        User mockUser = new User("validUser", "123", null);
        when(userServiceMock.login("validUser", "123")).thenReturn(mockUser);
        servlet.doPost(request, response);
        verify(session).setAttribute(AppConstants.SESSION_USER, mockUser);
        verify(response).sendRedirect("/");
        verify(dispatcher, never()).forward(any(), any());
    }

    @Test
    void doPost_InvalidLogin() throws ServletException, IOException {
        when(request.getParameter(AppConstants.PARAM_USERNAME)).thenReturn("badUser");
        when(request.getParameter(AppConstants.PARAM_PASSWORD)).thenReturn("wrong");
        when(userServiceMock.login("badUser", "wrong")).thenReturn(null);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(AppConstants.ATTR_ERROR_MESSAGE), eq(AppConstants.ERROR_INVALID_CRED));
        verify(request).getRequestDispatcher(AppConstants.JSP_LOGIN);
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }

}