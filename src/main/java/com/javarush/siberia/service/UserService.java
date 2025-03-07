package com.javarush.siberia.service;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.Stats;
import com.javarush.siberia.model.User;
import com.javarush.siberia.model.UserStats;
import com.javarush.siberia.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepo = new UserRepository();

    public boolean register(String username, String password, Role role) {
        if (userRepo.findByUsername(username) != null) return false;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        userRepo.save(user);
        return true;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        return (user != null && (password == null || user.getPassword().equals(password))) ? user : null;
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public boolean updateUser(String oldUsername, String newUsername, String newPassword, Role newRole) {
        return userRepo.updateUser(oldUsername, newUsername, newPassword, newRole);
    }

    public Map<String, Stats> getAllStats() {
        List<UserStats> userStatsList = userRepo.getAllStats();
        return userStatsList.stream()
                .collect(Collectors.toMap(
                        us -> us.getUser().getUsername(),
                        us -> new Stats(us.getTotalGames(), us.getWins(), us.getLosses())
                ));
    }
}