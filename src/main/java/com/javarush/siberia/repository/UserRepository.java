package com.javarush.siberia.repository;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.Stats;
import com.javarush.siberia.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final Map<String, User> USERS = new HashMap<>();
    private static final Map<String, Stats> USER_STATS = new HashMap<>();

    static {
        USERS.put("admin", new User("admin", "admin", Role.ADMIN));
        USERS.put("author", new User("author", "author", Role.AUTHOR));
        USERS.put("user", new User("user", "user", Role.USER));

        USER_STATS.put("admin", new Stats());
        USER_STATS.put("author", new Stats());
        USER_STATS.put("user", new Stats());
    }

    public User findByUsername(String username) {
        return USERS.get(username);
    }

    public void save(User user) {
        USERS.put(user.getUsername(), user);
        USER_STATS.putIfAbsent(user.getUsername(), new Stats());
    }

    public void incrementStats(String username, boolean victory) {
        Stats stats = this.USER_STATS.get(username);
        if (stats == null) {
            stats = new Stats();
            this.USER_STATS.put(username, stats);
        }
        stats.increment(victory);
    }

    public Stats getStats(String username) {
        return USER_STATS.get(username);
    }

    public Map<String, Stats> getAllStats() {
        return USER_STATS;
    }

    public Collection<User> getAllUsers() {
        return USERS.values();
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword, Role newRole) {
        User oldUser = USERS.get(oldUsername);
        if (oldUser == null) {
            return false;
        }

        if (newUsername == null || newUsername.trim().isEmpty()) {
            newUsername = oldUsername;
        }

        String finalPassword = (newPassword != null && !newPassword.isEmpty())
                ? newPassword
                : oldUser.getPassword();

        Role finalRole = (newRole != null) ? newRole : oldUser.getRole();

        if (newUsername.equals(oldUsername)) {
            User updatedUser = new User(oldUsername, finalPassword, finalRole);
            USERS.put(oldUsername, updatedUser);
            return true;
        } else {
            USERS.remove(oldUsername);

            User updatedUser = new User(newUsername, finalPassword, finalRole);
            USERS.put(newUsername, updatedUser);

            return true;
        }
    }

}