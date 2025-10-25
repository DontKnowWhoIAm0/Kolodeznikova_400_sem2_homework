package ru.kpfu.itis.Kolodeznikova.dto.workouts;

import java.util.List;

/**
 * DTO for transferring information about a workout request. Contains the request ID and a list of users who responded.
 */
public class WorkoutRequestDto {

    /** Unique identifier of the workout request */
    private int workoutRequestId;

    /** List of user IDs who responded to the request */
    private List<Integer> respondentIds;

    /**
     * Constructor to initialize the DTO.
     */
    public WorkoutRequestDto(int workoutRequestId, List<Integer> respondentIds) {
        this.workoutRequestId = workoutRequestId;
        this.respondentIds = respondentIds;
    }

    public int getWorkoutRequestId() {
        return workoutRequestId;
    }

    public List<Integer> getRespondentIds() {
        return respondentIds;
    }
}
