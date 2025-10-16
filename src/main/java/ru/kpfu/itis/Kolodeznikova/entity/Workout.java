package ru.kpfu.itis.Kolodeznikova.entity;

import ru.kpfu.itis.Kolodeznikova.entity.enums.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a workout that has been scheduled or completed.
 * A Workout includes the sport, city, participants, status and optionally the completion date.
 */
public class Workout {

    /** Unique identifier of the workout. */
    private int id;

    /** ID of the user who created the workout request. */
    private int creatorId;

    /** List of IDs of users who participated in the workout. */
    private List<Integer> participantsId;

    /** The sport chosen for this workout. */
    private Sports sports;

    /** The city where the workout took place. */
    private String city;

    /** Status of the workout: PENDING, COMPLETED, or CANCELED. */
    private WorkoutStatus status;

    /** The date when the workout was completed (only relevant if status is COMPLETED). */
    private LocalDate completedDate;

    /** Constructor including ID. */
    public Workout(int id, int creatorId, List<Integer> participantsId, Sports sports, String city, WorkoutStatus status, LocalDate completedDate) {
        this.id = id;
        this.creatorId = creatorId;
        this.participantsId = participantsId;
        this.sports = sports;
        this.city = city;
        this.status = status;
        this.completedDate = completedDate;
    }

    /** Constructor without ID for creating new workouts before saving to DB. */
    public Workout(int creatorId, List<Integer> participantsId, Sports sports, String city, WorkoutStatus status, LocalDate completedDate) {
        this.creatorId = creatorId;
        this.participantsId = participantsId;
        this.sports = sports;
        this.city = city;
        this.status = status;
        this.completedDate = completedDate;
    }

    public int getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public List<Integer> getParticipantsId() {
        return participantsId;
    }

    public Sports getSports() {
        return sports;
    }

    public String getCity() {
        return city;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }
}
