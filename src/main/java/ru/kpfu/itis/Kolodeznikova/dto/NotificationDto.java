package ru.kpfu.itis.Kolodeznikova.dto;

import java.time.LocalDate;

public class NotificationDto {

    /** Nickname of the user triggered the notification. */
    private String senderNickname;

    /** Text content of the notification. */
    private String text;

    /** Date when the notification was sent. */
    private LocalDate createdDate;

    /**
     * Constructor for creating notification dto.
     */
    public NotificationDto(String senderNickname, String text, LocalDate createdDate) {
        this.senderNickname = senderNickname;
        this.text = text;
        this.createdDate = createdDate;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getText() {
        return text;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

}
