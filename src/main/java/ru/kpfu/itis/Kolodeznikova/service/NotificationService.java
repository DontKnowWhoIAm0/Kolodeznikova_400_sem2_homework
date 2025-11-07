package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.dto.NotificationDto;
import ru.kpfu.itis.Kolodeznikova.entity.misc.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationService {

    List<NotificationDto> getUserNotifications(int userId) throws SQLException;
    void addNotification(int senderId, String text, List<Integer> recipientIds) throws SQLException;
    Notification getNotificationById(int notificationId) throws SQLException;
}
