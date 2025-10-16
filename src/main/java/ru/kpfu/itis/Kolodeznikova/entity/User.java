package ru.kpfu.itis.Kolodeznikova.entity;

import ru.kpfu.itis.Kolodeznikova.entity.enums.Gender;
import ru.kpfu.itis.Kolodeznikova.entity.enums.WayOfCommunication;

/**
 * Represents a user.
 * A user has personal information, gender, and preferred way of communication.
 */
public class User {

    /** Unique identifier of the user. */
    private int id;

    /** First name of the user. */
    private final String name;

    /** Last name of the user. */
    private final String lastName;

    /** Nickname of the user, used for display. */
    private final String nickname;

    /** Gender of the user (MALE or FEMALE). */
    private final Gender gender;

    /** Preferred way of communication for contacting the user. */
    private final WayOfCommunication wayOfCommunication;

    /** Contact information corresponding to the selected way of communication. */
    private final String contactValue;

    /** Constructor including ID. */
    public User(int id, String name, String lastName, String nickname, Gender gender, WayOfCommunication wayOfCommunication, String contactValue) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.gender = gender;
        this.wayOfCommunication = wayOfCommunication;
        this.contactValue = contactValue;
    }

    /** Constructor without ID for creating new users before saving to DB. */
    public User(String name, String lastName, String nickname, Gender gender, WayOfCommunication wayOfCommunication, String contactValue) {
        this.name = name;
        this.lastName = lastName;
        this.nickname = nickname;
        this.gender = gender;
        this.wayOfCommunication = wayOfCommunication;
        this.contactValue = contactValue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public WayOfCommunication getWayOfCommunication() {
        return wayOfCommunication;
    }

    public String getContactValue() {
        return contactValue;
    }
}


