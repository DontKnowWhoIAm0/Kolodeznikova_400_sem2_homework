package ru.kpfu.itis.Kolodeznikova.entity.misc;

import java.time.LocalDate;

public class Notification {

    /** Unique identifier of the notification. */
    private int id;

    /** ID of the user triggered the notification. */
    private int senderId;

    /** Text content of the notification. */
    private String text;

    /** Date when the notification was sent. */
    private LocalDate createdDate;

    /**
     * Constructor for creating a new notification before saving to the database.
     */
    public Notification(int senderId, String text) {
        this.senderId = senderId;
        this.text = text;
    }

    /**
     * Constructor including ID and creation date for loading notification from the database.
     */
    public Notification(int id, int senderId, String text, LocalDate createdDate) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
