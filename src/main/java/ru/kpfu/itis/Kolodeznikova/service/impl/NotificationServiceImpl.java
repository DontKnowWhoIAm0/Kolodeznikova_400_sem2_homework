package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.misc.NotificationDao;
import ru.kpfu.itis.Kolodeznikova.dto.NotificationDto;
import ru.kpfu.itis.Kolodeznikova.entity.misc.Notification;
import ru.kpfu.itis.Kolodeznikova.service.NotificationService;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;
    private final UserService userService;

    public NotificationServiceImpl(NotificationDao notificationDao, UserService userService) {
        this.notificationDao = notificationDao;
        this.userService = userService;
    }

    @Override
    public List<NotificationDto> getUserNotifications(int userId) throws SQLException {
        List<Notification> notifications = notificationDao.getAllUserNotifications(userId);
        List<NotificationDto> notificationDtos = new ArrayList<>();

        for (Notification notification : notifications) {
            notificationDtos.add(new NotificationDto(
                    userService.findUserById(notification.getSenderId()).getNickname(),
                    notification.getText(),
                    notification.getCreatedDate()));
        }

        return notificationDtos;
    }

    @Override
    public void addNotification(int senderId, String text, List<Integer> recipientIds) throws SQLException {
        Notification notification = new Notification(senderId, text);
        int notificationId = notificationDao.create(notification);

        for (Integer recipientId : recipientIds) {
            notificationDao.addRecipient(notificationId, recipientId);
        }
    }

    @Override
    public Notification getNotificationById(int notificationId) throws SQLException {
        return notificationDao.findById(notificationId);
    }
}
