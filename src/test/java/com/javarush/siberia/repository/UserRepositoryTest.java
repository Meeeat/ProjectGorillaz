package com.javarush.siberia.repository;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
    }

    @Test
    void testFindByUsername_NotFound() {
        User user = userRepository.findByUsername("nonexisting");
        Assertions.assertNull(user);
    }

    @Test
    void testSave_NewUser() {
        User newUser = new User("testuser", "pass", Role.USER);
        userRepository.save(newUser);
        User saved = userRepository.findByUsername("testuser");
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("testuser", saved.getUsername());
    }

    @Test
    void testIncrementStats() {
        userRepository.incrementStats("user", true);  // победа
        userRepository.incrementStats("user", false); // поражение
        var stats = userRepository.getStats("user");
        Assertions.assertNotNull(stats);
        Assertions.assertEquals(2, stats.getTotal());
        Assertions.assertEquals(1, stats.getWins());
        Assertions.assertEquals(1, stats.getLosses());
    }

    @Test
    void testUpdateUser_SameName() {
        boolean result = userRepository.updateUser("admin", "admin", "newpass", Role.AUTHOR);
        Assertions.assertTrue(result);
        User updated = userRepository.findByUsername("admin");
        Assertions.assertEquals("newpass", updated.getPassword());
        Assertions.assertEquals(Role.AUTHOR, updated.getRole());
    }

    @Test
    void testUpdateUser_ChangeNameNoStats() {
        boolean result = userRepository.updateUser("author", "author2", "randompass", Role.USER);
        Assertions.assertTrue(result);

        User oldUser = userRepository.findByUsername("author");
        Assertions.assertNull(oldUser);

        User newUser = userRepository.findByUsername("author2");
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("randompass", newUser.getPassword());
        Assertions.assertEquals(Role.USER, newUser.getRole());
    }

}