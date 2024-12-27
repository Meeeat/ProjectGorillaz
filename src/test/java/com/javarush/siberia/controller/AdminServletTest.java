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
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminServletTest {

    private AdminServlet servlet;
    private UserService userServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new AdminServlet();
        userServiceMock = mock(UserService.class);
        try {
            var field = AdminServlet.class.getDeclaredField("userService");
            field.setAccessible(true);
            field.set(servlet, userServiceMock);
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
    void doGet_UserNotAdmin_SendError403() throws ServletException, IOException {
        User user = new User("testUser", "pass", Role.USER);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        servlet.doGet(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
        verify(dispatcher, never()).forward(any(), any());
    }

    @Test
    void doGet_AdminOk_ForwardToAdminJSP() throws ServletException, IOException {
        User admin = new User("admin", "admin", Role.ADMIN);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(admin);
        var userRepoMock = mock(com.javarush.siberia.repository.UserRepository.class);
        when(userServiceMock.getUserRepository()).thenReturn(userRepoMock);
        when(userRepoMock.getAllUsers()).thenReturn(List.of(admin));
        servlet.doGet(request, response);
        verify(response, never()).sendError(anyInt()); // не было ошибок
        verify(request).setAttribute(eq(AppConstants.ATTR_USERS), any());
        verify(request).setAttribute(eq(AppConstants.ATTR_TITLE), eq("Admin-panel"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_UserNotAdmin_SendError403() throws ServletException, IOException {
        User user = new User("testUser", "pass", Role.USER);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(user);
        servlet.doPost(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), anyString());
        verify(dispatcher, never()).forward(any(), any());
    }

    @Test
    void doPost_AdminOk_UpdateUser_Success() throws ServletException, IOException {
        User admin = new User("admin", "admin", Role.ADMIN);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(admin);
        var userRepoMock = mock(com.javarush.siberia.repository.UserRepository.class);
        when(userServiceMock.getUserRepository()).thenReturn(userRepoMock);
        when(request.getParameter(AppConstants.PARAM_OLD_USERNAME)).thenReturn("oldUser");
        when(request.getParameter(AppConstants.PARAM_NEW_USERNAME)).thenReturn("newUser");
        when(request.getParameter(AppConstants.PARAM_NEW_PASSWORD)).thenReturn("12345");
        when(request.getParameter(AppConstants.PARAM_NEW_ROLE)).thenReturn("AUTHOR");
        when(userRepoMock.updateUser(eq("oldUser"), eq("newUser"), eq("12345"), eq(Role.AUTHOR))).thenReturn(true);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(AppConstants.ATTR_MESSAGE), eq(AppConstants.SUCCESS_USER_UPDATE));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_AdminOk_UpdateUser_Failure() throws ServletException, IOException {
        User admin = new User("admin", "admin", Role.ADMIN);
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(admin);
        var userRepoMock = mock(com.javarush.siberia.repository.UserRepository.class);
        when(userServiceMock.getUserRepository()).thenReturn(userRepoMock);
        when(request.getParameter(AppConstants.PARAM_OLD_USERNAME)).thenReturn("nonExistUser");
        when(userRepoMock.updateUser(eq("nonExistUser"), any(), any(), any())).thenReturn(false);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq(AppConstants.ATTR_ERROR), eq(AppConstants.ERROR_CANT_UPDATE_USER));
        verify(dispatcher).forward(request, response);
    }

}