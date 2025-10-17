package ru.kpfu.itis.Kolodeznikova.dao.impl;

import ru.kpfu.itis.Kolodeznikova.dao.UserDao;
import ru.kpfu.itis.Kolodeznikova.entity.User;
import ru.kpfu.itis.Kolodeznikova.entity.enums.*;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class that provides CRUD operations for User entities.
 */
public class UserDaoImpl implements UserDao {

    /** SQL query to create the "users" table if it does not exist. */
    private static final String USER_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                login varchar(100) UNIQUE NOT NULL ,
                password varchar(512) NOT NULL,
                name varchar(50) NOT NULL,
                lastname varchar(50) NOT NULL,
                nickname varchar(50) UNIQUE NOT NULL,
                gender varchar(10) NOT NULL,
                wayOfCommunication varchar(20) NOT NULL,
                contactValue varchar(100) NOT NULL
            );
            """;

    /** SQL query to add a new user. */
    private static final String ADD_USER_QUERY = """
            INSERT INTO users (login, password, name, lastname, nickname, gender, wayOfCommunication, contactValue)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    /** SQL query to get a user by login. */
    private static final String GET_USER_BY_LOGIN_QUERY = """
            SELECT * FROM users WHERE login = ?;
            """;

    /** SQL query to get a user by id. */
    private static final String GET_USER_BY_ID_QUERY = """
            SELECT * FROM users WHERE id = ?;
            """;

    /** SQL query to update a user's information. */
    private static final String UPDATE_USER_INFORMATION_QUERY = """
            UPDATE users
            SET name = ?, lastname = ?, nickname = ?, wayOfCommunication = ?, contactValue = ?
            WHERE id = ?;
            """;

    /** SQL query to delete a user by ID. */
    private static final String DELETE_USER_QUERY = """
            DELETE FROM users WHERE id = ?;
            """;

    /** SQL query to get all users. */
    private static final String GET_ALL_USERS_QUERY = """
            SELECT * FROM users;
            """;


    private final ConnectionPool connectionPool;

    /**
     * Constructor initializes the DAO and ensures that the users table exists.
     */
    public UserDaoImpl(ConnectionPool connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(USER_TABLE_CREATE_QUERY);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Adds a new user into the database.
     */
    @Override
    public void create(User user) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getNickname());
            statement.setString(6, user.getGender().toString());
            statement.setString(7, user.getWayOfCommunication().toString());
            statement.setString(8, user.getContactValue());
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Finds a user by their ID.
     */
    @Override
    public User findById(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = makeUser(resultSet);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
    }

    /**
     * Finds a user by their login.
     */
    @Override
    public User findByLogin(String login) throws SQLException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_QUERY)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = makeUser(resultSet);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
    }

    /**
     * Updates a user's information (name, lastname, nickname, way of communication and contact data) in the database.
     */
    @Override
    public void update(User user) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_INFORMATION_QUERY)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getNickname());
            statement.setString(4, user.getWayOfCommunication().toString());
            statement.setString(5, user.getContactValue());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Deletes a user from the database by ID.
     */
    @Override
    public void delete(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Returns all users from the database.
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                users.add(makeUser(resultSet));
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return users;
    }

    /**
     * Additional method to make a User object from ResultSet.
     */
    private User makeUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("lastname"),
                resultSet.getString("nickname"),
                Gender.valueOf(resultSet.getString("gender")),
                WayOfCommunication.valueOf(resultSet.getString("wayOfCommunication")),
                resultSet.getString("contactValue")
        );
    }
}