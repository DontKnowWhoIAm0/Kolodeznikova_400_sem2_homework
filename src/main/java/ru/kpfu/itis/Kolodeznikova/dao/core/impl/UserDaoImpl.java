package ru.kpfu.itis.Kolodeznikova.dao.core.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.UserDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
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
            CREATE TABLE IF NOT EXISTS sb_db.users (
                id BIGSERIAL PRIMARY KEY,
                login varchar(100) UNIQUE NOT NULL ,
                password_hash varchar(512) NOT NULL,
                name varchar(50) NOT NULL,
                lastname varchar(50) NOT NULL,
                nickname varchar(50) UNIQUE NOT NULL,
                gender varchar(10) NOT NULL,
                way_of_communication varchar(20) NOT NULL,
                contact_value varchar(100) NOT NULL,
                profile_image varchar(100)
            );
            """;

    /** SQL query to add a new user. */
    private static final String ADD_USER_QUERY = """
            INSERT INTO sb_db.users (login, password_hash, name, lastname, nickname, gender, way_of_communication, contact_value, profile_image)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    /** SQL query to get a user by login. */
    private static final String GET_USER_BY_LOGIN_QUERY = """
            SELECT * FROM sb_db.users WHERE login = ?;
            """;

    /** SQL query to get a user by ID. */
    private static final String GET_USER_BY_ID_QUERY = """
            SELECT * FROM sb_db.users WHERE id = ?;
            """;

    /** SQL query to get a user by nickname. */
    private static final String GET_USER_BY_NICKNAME_QUERY = """
            SELECT * FROM sb_db.users WHERE nickname = ?;
            """;

    /** SQL query to update a user's information. */
    private static final String UPDATE_USER_INFORMATION_QUERY = """
            UPDATE sb_db.users
            SET name = ?, lastname = ?, nickname = ?, way_of_communication = ?, contact_value = ?, profile_image = ?
            WHERE id = ?;
            """;

    /** SQL query to delete a user by ID. */
    private static final String DELETE_USER_QUERY = """
            DELETE FROM sb_db.users WHERE id = ?;
            """;

    /** SQL query to get all users. */
    private static final String GET_ALL_USERS_QUERY = """
            SELECT * FROM sb_db.users;
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
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getName());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getNickname());
            statement.setString(6, user.getGender().toString());
            statement.setString(7, user.getWayOfCommunication().toString());
            statement.setString(8, user.getContactValue());
            statement.setString(9, user.getProfileImage());
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
     * Finds a user by their nickname.
     */
    @Override
    public User findByNickname(String nickname) throws SQLException {
        Connection connection = connectionPool.getConnection();
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_NICKNAME_QUERY)) {
            statement.setString(1, nickname);
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
            statement.setString(6, user.getProfileImage());
            statement.setInt(7, user.getId());
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
                resultSet.getString("password_hash"),
                resultSet.getString("name"),
                resultSet.getString("lastname"),
                resultSet.getString("nickname"),
                Gender.valueOf(resultSet.getString("gender")),
                WayOfCommunication.valueOf(resultSet.getString("way_of_communication")),
                resultSet.getString("contact_value"),
                resultSet.getString("profile_image")
        );
    }
}