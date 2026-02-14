package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.UserDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
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

    /**
     * Checks if a user with the given nickname exists.
     */
    @Override
    public boolean nicknameExists(String nickname) throws SQLException {
        return userDao.findByNickname(nickname) != null;
    }

    /**
     * Checks the correctness of the login and password pair.
     */
    public boolean checkPasswordAndLogin(String login, String passwordHash) throws SQLException {
        User user = userDao.findByLogin(login);
        if (user == null) {
            return false;
        }
        return passwordHash.equals(user.getPasswordHash());
    }

    /**
     * Finds user's ID by login and returns its.
     */
    @Override
    public int findUserIdByLogin(String login) throws SQLException {
        return userDao.findByLogin(login).getId();
    }

    /**
     * Finds user's ID by login and returns its.
     */
    @Override
    public User findUserById(int id) throws SQLException {
        return userDao.findById(id);
    }
}
