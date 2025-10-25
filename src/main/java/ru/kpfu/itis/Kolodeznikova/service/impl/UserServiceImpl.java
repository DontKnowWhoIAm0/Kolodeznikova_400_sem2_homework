package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.UserDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.util.PasswordUtil;

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
    public boolean checkPasswordAndLogin(String login, String password) throws SQLException {
        User user = userDao.findByLogin(login);
        if (user == null) {
            return false;
        }
        return PasswordUtil.encrypt(password).equals(user.getPasswordHash());
    }
}
