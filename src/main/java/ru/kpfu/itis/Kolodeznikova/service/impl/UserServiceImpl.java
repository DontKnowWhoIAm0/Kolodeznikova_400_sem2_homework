package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.UserDao;
import ru.kpfu.itis.Kolodeznikova.entity.User;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.sql.SQLException;

/**
 * Service class that provides high-level user management operations.
 */
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user.
     */
    @Override
    public void registerUser(User user) throws SQLException {
        userDao.create(user);
    }

    /**
     * Checks if a user with the given login exists.
     */
    @Override
    public boolean loginExists(String login) throws SQLException {
        return userDao.findByLogin(login) != null;
    }
}
