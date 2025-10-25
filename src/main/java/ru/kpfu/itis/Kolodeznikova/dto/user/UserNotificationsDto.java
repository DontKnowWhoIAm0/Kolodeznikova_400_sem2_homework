package ru.kpfu.itis.Kolodeznikova.dto.user;

import ru.kpfu.itis.Kolodeznikova.entity.misc.Notification;

import java.util.List;

/**
 * DTO for transferring a user's notifications. Contains the user ID and a list of their notifications.
 */
public class UserNotificationsDto {

    /** User's unique identifier */
    private int userId;

    /** List of user's notifications */
    private List<Notification> notifications;

    /**
     * Constructor to initialize the DTO.
     */
    public UserNotificationsDto(int userId, List<Notification> notifications) {
        this.userId = userId;
        this.notifications = notifications;
    }

    public int getUserId() {
        return userId;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
