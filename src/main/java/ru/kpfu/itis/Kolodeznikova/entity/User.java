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

    /** Login of the user. */
    private String login;

    /** Password of the user. */
    private String password;

    /** First name of the user. */
    private final String name;

    /** Last name of the user. */
    private final String lastname;

    /** Nickname of the user, used for display. */
    private final String nickname;

    /** Gender of the user (MALE or FEMALE). */
    private final Gender gender;

    /** Preferred way of communication for contacting the user. */
    private final WayOfCommunication wayOfCommunication;

    /** Contact information corresponding to the selected way of communication. */
    private final String contactValue;

    /** File path to the user's profile image. */
    private final String profileImage;

    /** Constructor including ID. */
    public User(int id, String login, String password, String name, String lastname, String nickname, Gender gender, WayOfCommunication wayOfCommunication, String contactValue, String profileImage) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
        this.gender = gender;
        this.wayOfCommunication = wayOfCommunication;
        this.contactValue = contactValue;
        this.profileImage = profileImage;
    }

    /** Constructor without ID for creating new users before saving to DB. */
    public User(String name, String login, String password, String lastname, String nickname, Gender gender, WayOfCommunication wayOfCommunication, String contactValue, String profileImage) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.nickname = nickname;
        this.gender = gender;
        this.wayOfCommunication = wayOfCommunication;
        this.contactValue = contactValue;
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
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

    public String getProfileImage() {
        return profileImage;
    }
}


