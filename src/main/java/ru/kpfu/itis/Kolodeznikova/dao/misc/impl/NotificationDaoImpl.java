package ru.kpfu.itis.Kolodeznikova.dao.misc.impl;

import ru.kpfu.itis.Kolodeznikova.dao.misc.NotificationDao;
import ru.kpfu.itis.Kolodeznikova.entity.misc.Notification;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class that provides CRUD operations for Notification entities.
 * Manages both notifications and their recipients.
 */
public class NotificationDaoImpl implements NotificationDao {

    /**
     * SQL query to create the "notifications" table if it does not exist.
     */
    private static final String CREATE_NOTIFICATIONS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS sb_db.notifications (
                id BIGSERIAL PRIMARY KEY,
                sender_id BIGINT NOT NULL REFERENCES sb_db.users(id) ON DELETE CASCADE,
                text TEXT NOT NULL,
                created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
            );
            """;

    /**
     * SQL query to create the "recipients" table if it does not exist to link notifications and recipients.
     */
    private static final String CREATE_RECIPIENTS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS sb_db.recipients (
                notification_id BIGINT REFERENCES sb_db.notifications(id) ON DELETE CASCADE,
                recipient_id BIGINT REFERENCES sb_db.users(id) ON DELETE CASCADE,
                PRIMARY KEY (notification_id, recipient_id)
            );
            """;

    /**
     * SQL query to add a new notification and return its generated ID.
     */
    private static final String ADD_NOTIFICATION_QUERY = """
            INSERT INTO sb_db.notifications (sender_id, text) VALUES (?, ?)
            RETURNING id;
            """;

    /**
     * SQL query to add a recipient to a notification.
     */
    private static final String ADD_RECIPIENT_QUERY = """
            INSERT INTO sb_db.recipients (notification_id, recipient_id) VALUES (?, ?);
            """;

    /**
     * SQL query to get a notification by its ID.
     */
    private static final String GET_NOTIFICATION_BY_ID_QUERY = """
            SELECT * FROM sb_db.notifications WHERE id = ?;
            """;

    /**
     * SQL query to get all notification IDs for a recipient by their ID.
     */
    private static final String GET_NOTIFICATIONS_BY_RECIPIENT_ID_QUERY = """
            SELECT n.id, n.sender_id, n.text, n.created_date
            FROM sb_db.notifications n JOIN sb_db.recipients r ON n.id = r.notification_id
            WHERE r.recipient_id = ?;
            """;

    private final ConnectionPool connectionPool;

    /**
     * Constructor initializes the DAO and ensures that the notifications and recipients tables exist.
     */
    public NotificationDaoImpl(ConnectionPool connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_NOTIFICATIONS_TABLE_QUERY);
            statement.execute(CREATE_RECIPIENTS_TABLE_QUERY);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Adds a new notification to the database and returns its ID.
     */
    @Override
    public int create(Notification notification) throws SQLException {
        Connection connection = connectionPool.getConnection();
        int id = -1;
        try (PreparedStatement statement = connection.prepareStatement(ADD_NOTIFICATION_QUERY)) {
            statement.setInt(1, notification.getSenderId());
            statement.setString(2, notification.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    /**
     * Adds a recipient to an existing notification.
     */
    @Override
    public void addRecipient(int notificationId, int recipientId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_RECIPIENT_QUERY);) {
            statement.setInt(1, notificationId);
            statement.setInt(2, recipientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Finds a notification by its ID.
     */
    @Override
    public Notification findById(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Notification notification = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_NOTIFICATION_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                notification = makeNotification(resultSet);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return notification;
    }

    /**
     * Retrieves all notifications received by a specific user.
     */
    @Override
    public List<Notification> getAllUserNotifications(int userId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Notification> notifications = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_NOTIFICATIONS_BY_RECIPIENT_ID_QUERY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                notifications.add(makeNotification(resultSet));
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return notifications;
    }

    /**
     * Additional method to make a Notification object from ResultSet.
     */
    private Notification makeNotification(ResultSet resultSet) throws SQLException {
        return new Notification(
                resultSet.getInt("id"),
                resultSet.getInt("sender_id"),
                resultSet.getString("text"),
                (resultSet.getDate("created_date") != null) ? resultSet.getDate("created_date").toLocalDate() : null
        );
    }
}
