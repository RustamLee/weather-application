package org.example.weather.service;

import org.example.weather.dao.UserDAO;
import org.example.weather.model.User;
import org.example.weather.session.Session;
import org.example.weather.session.SessionManager;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean registerUser(String username, String rawPassword) {
        if (userDAO.findByUsername(username).isPresent()) {
            return false; // имя пользователя уже занято
        }

        // шифруем пароль с помощью BCrypt
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // создаем нового пользователя
        User newUser = new User(username, hashedPassword);
        userDAO.save(newUser);
        return true;
    }

    @Override
    public User authenticateUser(String username, String rawPassword) {
        User user = userDAO.findByUsername(username).orElse(null);
        if (user != null && BCrypt.checkpw(rawPassword, user.getPasswordHash())) {
            return user; // пароль верен, возвращаем пользователя
        }
        return null; // неверный пароль
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id).orElse(null); // возвращаем пользователя по ID
    }

    @Override
    public Optional<User> findBySessionId(String sessionId) {
        Session session = SessionManager.getSession(sessionId);
        if (session == null) return Optional.empty();
        return Optional.ofNullable(findById(session.getUserId()));
    }

}
