package ru.kpfu.itis.Kolodeznikova.dao;

import ru.kpfu.itis.Kolodeznikova.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for working with User entities.
 */
public interface UserDao extends Dao<User> {
    User findByLogin(String login) throws SQLException;
    void update(User user) throws SQLException;
    List<User> getAllUsers() throws SQLException;
}
