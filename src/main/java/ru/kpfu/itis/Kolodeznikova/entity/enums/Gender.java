package ru.kpfu.itis.Kolodeznikova.entity.enums;

/**
 * Enum representing the gender of a user.
 * Each constant includes a display name in Russian.
 */

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String type;

    Gender(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
