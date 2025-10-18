package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.entity.User;

import java.sql.SQLException;

/**
 * Service interface for managing User entities.
 */
public interface UserService {
    void registerUser(User user) throws SQLException;
    boolean loginExists(String login) throws SQLException;
}
