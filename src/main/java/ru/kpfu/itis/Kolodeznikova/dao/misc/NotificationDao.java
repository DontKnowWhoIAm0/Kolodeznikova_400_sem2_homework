package ru.kpfu.itis.Kolodeznikova.dao.misc;

import ru.kpfu.itis.Kolodeznikova.entity.misc.Notification;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for working with Notification entities.
 */
public interface NotificationDao {
    int create(Notification notification) throws SQLException;
    void addRecipient(int notificationId, int recipientId) throws SQLException;
    Notification findById(int id) throws SQLException;
    List<Notification> getAllUserNotifications(int userId) throws SQLException;
}
