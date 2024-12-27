package com.javarush.siberia.controller;

import com.javarush.siberia.model.Role;
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

class RegisterServletTest {

    private RegisterServlet servlet;
    private UserService userServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new RegisterServlet();
        userServiceMock = mock(UserService.class);
        Field field = RegisterServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userServiceMock);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(AppConstants.JSP_REGISTER)).thenReturn(dispatcher);
    }

    @Test
    void doGet_ForwardRegisterJSP() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_Success() throws ServletException, IOException {
        when(request.getParameter(AppConstants.PARAM_USERNAME)).thenReturn("newUser");
        when(request.getParameter(AppConstants.PARAM_PASSWORD)).thenReturn("123");
        when(userServiceMock.register("newUser", "123", Role.USER)).thenReturn(true);
        User newUser = new User("newUser","123",Role.USER);
        when(userServiceMock.login("newUser", "123")).thenReturn(newUser);
        servlet.doPost(request, response);
        verify(session).setAttribute(AppConstants.SESSION_USER, newUser);
        verify(response).sendRedirect("/");
    }

    @Test
    void doPost_Failure_AlreadyExists() throws ServletException, IOException {
        when(request.getParameter(AppConstants.PARAM_USERNAME)).thenReturn("admin");
        when(request.getParameter(AppConstants.PARAM_PASSWORD)).thenReturn("admin");
        when(userServiceMock.register("admin", "admin", Role.USER)).thenReturn(false);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(AppConstants.ATTR_ERROR_MESSAGE), eq(AppConstants.ERROR_USER_EXISTS));
        verify(dispatcher).forward(request, response);
    }

}