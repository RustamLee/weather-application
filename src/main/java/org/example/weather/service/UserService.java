package org.example.weather.service;

import org.example.weather.model.User;

import java.util.Optional;

public interface UserService {

    boolean registerUser(String username, String rawPassword);
    User authenticateUser(String username, String rawPassword);
    User findById(int id);

    Optional<User> findBySessionId(String sessionId);
}
