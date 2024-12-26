package com.javarush.siberia.repository;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final Map<String, User> USERS = new HashMap<>();
    private static final Map<String,Integer> USER_STATS = new HashMap<>();

    static {
        USERS.put("admin", new User("admin", "admin", Role.ADMIN));
        USERS.put("author", new User("author", "author", Role.AUTHOR));
        USERS.put("user", new User("user", "user", Role.USER));
        USER_STATS.put("admin",0);
        USER_STATS.put("author",0);
        USER_STATS.put("user",0);
    }

    public User findByUsername(String username) {
        return USERS.get(username);
    }

    public void save(User user) {
        USERS.put(user.getUsername(), user);
        USER_STATS.put(user.getUsername(),0);
    }

    public void incrementGamesPlayed(String username) {
        USER_STATS.put(username, USER_STATS.getOrDefault(username,0)+1);
    }

    public Map<String,Integer> getUserStats() {
        return USER_STATS;
    }

    public Collection<User> getAllUsers() {
        return USERS.values();
    }

    public boolean updateUser(String username, String newPassword, Role newRole) {
        User user = USERS.get(username);
        if (user == null) return false;
        User updatedUser = new User(username, newPassword != null && !newPassword.isEmpty() ? newPassword : user.getPassword(),
                newRole != null ? newRole : user.getRole());
        USERS.put(username, updatedUser);
        return true;
    }

}