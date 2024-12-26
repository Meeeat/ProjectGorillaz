package com.javarush.siberia.repository;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.Stats;
import com.javarush.siberia.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Stats> stats = new HashMap<>();

    {
        users.put("admin", new User("admin", "admin", Role.ADMIN));
        users.put("author", new User("author", "author", Role.AUTHOR));
        users.put("user", new User("user", "user", Role.USER));

        stats.put("admin", new Stats());
        stats.put("author", new Stats());
        stats.put("user", new Stats());
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
        stats.putIfAbsent(user.getUsername(), new Stats());
    }

    public void incrementStats(String username, boolean victory) {
        Stats stats = this.stats.get(username);
        if (stats == null) {
            stats = new Stats();
            this.stats.put(username, stats);
        }
        stats.increment(victory);
    }

    public Stats getStats(String username) {
        return stats.get(username);
    }

    public Map<String, Stats> getAllStats() {
        return stats;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword, Role newRole) {
        User oldUser = users.get(oldUsername);
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
            users.put(oldUsername, updatedUser);
            return true;
        } else {
            users.remove(oldUsername);

            User updatedUser = new User(newUsername, finalPassword, finalRole);
            users.put(newUsername, updatedUser);

            return true;
        }
    }

}