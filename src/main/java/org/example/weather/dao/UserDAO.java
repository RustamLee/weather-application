package org.example.weather.dao;

import org.example.weather.model.User;

import java.util.Optional;

public interface UserDAO {
    void save(User user);
    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);

}
