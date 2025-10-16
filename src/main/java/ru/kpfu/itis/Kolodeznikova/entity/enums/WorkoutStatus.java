package ru.kpfu.itis.Kolodeznikova.entity.enums;

/**
 * Enum representing the status of a workout.
 * Each constant includes a display name in Russian.

 * PENDING   - The workout is scheduled and awaiting confirmation.
 * COMPLETED - The workout has been completed.
 * CANCELED  - The workout was canceled.
 */

public enum WorkoutStatus {
    PENDING("В ожидании"),
    COMPLETED("Выполнена"),
    CANCELED("Отменена");

    private final String status;

    WorkoutStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
