package ru.kpfu.itis.Kolodeznikova.dto;

public class UserDto {
    private String username;
    private String secondName;

    public UserDto(String username, String secondName) {
        this.username = username;
        this.secondName = secondName;
    }

    public String getUsername() { return username; }
    public String getSecondName() { return secondName; }
}