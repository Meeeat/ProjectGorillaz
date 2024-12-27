package com.javarush.siberia.controller;

import com.javarush.siberia.model.Stats;
import com.javarush.siberia.service.UserService;
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

class StatsServletTest {

    private StatsServlet servlet;
    private UserService userServiceMock;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new StatsServlet();
        userServiceMock = mock(UserService.class);
        Field field = StatsServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(servlet, userServiceMock);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(AppConstants.JSP_STATS)).thenReturn(dispatcher);
    }

    @Test
    void doGet_ForwardToStatsJSP() throws ServletException, IOException {
        Map<String, Stats> mockStats = new HashMap<>();
        Stats s = new Stats();
        s.setTotal(10);
        s.setWins(5);
        s.setLosses(5);
        mockStats.put("user", s);
        var userRepoMock = mock(com.javarush.siberia.repository.UserRepository.class);
        when(userRepoMock.getAllStats()).thenReturn(mockStats);
        when(userServiceMock.getUserRepository()).thenReturn(userRepoMock);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("allStats"), eq(mockStats));
        verify(dispatcher).forward(request, response);
    }

}