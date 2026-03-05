package ru.kpfu.itis.Kolodeznikova.dto;

public class UserDto {
    private String firstName;
    private String secondName;

    public UserDto(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() { return firstName; }
    public String getSecondName() { return secondName; }
}