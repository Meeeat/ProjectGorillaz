package com.javarush.siberia.service;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import org.junit.jupiter.api.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService();
    }

    @Test
    void testRegisterAndLogin() {
        boolean result = userService.register("testUser", "123", Role.USER);
        Assertions.assertTrue(result);

        User user = userService.login("testUser", "123");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("testUser", user.getUsername());
        Assertions.assertEquals(Role.USER, user.getRole());
    }

    @Test
    void testRegisterExistingUser() {
        boolean result = userService.register("admin", "anypass", Role.USER);
        Assertions.assertFalse(result);
    }

    @Test
    void testLoginWrongPassword() {
        User user = userService.login("admin", "wrong");
        Assertions.assertNull(user);
    }

}