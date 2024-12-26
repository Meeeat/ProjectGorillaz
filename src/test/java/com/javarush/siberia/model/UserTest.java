package com.javarush.siberia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserFields() {
        User user = new User("testUser", "pass123", Role.USER);
        Assertions.assertEquals("testUser", user.getUsername());
        Assertions.assertEquals("pass123", user.getPassword());
        Assertions.assertEquals(Role.USER, user.getRole());
    }
}