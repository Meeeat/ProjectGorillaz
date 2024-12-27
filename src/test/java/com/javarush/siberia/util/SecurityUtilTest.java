package com.javarush.siberia.util;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import static org.mockito.Mockito.*;

class SecurityUtilTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void checkAdmin_UserNull() throws IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(null);
        boolean result = SecurityUtil.checkAdmin(request, response);
        Assertions.assertFalse(result);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), eq(AppConstants.NO_ACCESS));
    }

    @Test
    void checkAdmin_NotAdmin() throws IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(
                new User("u","p", Role.USER)
        );
        boolean result = SecurityUtil.checkAdmin(request, response);
        Assertions.assertFalse(result);
        verify(response).sendError(eq(HttpServletResponse.SC_FORBIDDEN), eq(AppConstants.NO_ACCESS));
    }

    @Test
    void checkAdmin_AdminOk() throws IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(
                new User("admin","admin",Role.ADMIN)
        );
        boolean result = SecurityUtil.checkAdmin(request, response);
        Assertions.assertTrue(result);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void checkLoggedIn_UserNull_RedirectLogin() throws IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(null);
        boolean result = SecurityUtil.checkLoggedIn(request, response);
        Assertions.assertFalse(result);
        verify(response).sendRedirect("/login");
    }

    @Test
    void checkLoggedIn_UserNotNull_Ok() throws IOException {
        when(session.getAttribute(AppConstants.SESSION_USER)).thenReturn(
                new User("u","p",Role.USER)
        );
        boolean result = SecurityUtil.checkLoggedIn(request, response);
        Assertions.assertTrue(result);
        verify(response, never()).sendRedirect(anyString());
    }

}