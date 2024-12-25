package com.javarush.siberia.service;

import com.javarush.siberia.model.Role;
import com.javarush.siberia.model.User;
import com.javarush.siberia.repository.UserRepository;

public class UserService {
    private final UserRepository userRepo = new UserRepository();

    public boolean register(String username, String password, Role role) {
        if (userRepo.findByUsername(username) != null) {
            return false;
        }
        userRepo.save(new User(username, password, role));
        return true;
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public UserRepository getUserRepository() {
        return userRepo;
    }

}