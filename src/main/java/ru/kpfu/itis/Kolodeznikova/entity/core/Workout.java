package ru.kpfu.itis.Kolodeznikova.entity.core;

import ru.kpfu.itis.Kolodeznikova.entity.enums.*;

import java.time.LocalDate;

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
    private int participantId;

    /** The sport chosen for this workout. */
    private Sports sport;

    /** The city where the workout took place. */
    private String city;

    /** Status of the workout: PENDING, COMPLETED, or CANCELED. */
    private WorkoutStatus status;

    /** The date when the workout was completed (only relevant if status is COMPLETED). */
    private LocalDate completedDate;

    /** Constructor including ID. */
    public Workout(int id, int creatorId, int participantId, Sports sport, String city, WorkoutStatus status, LocalDate completedDate) {
        this.id = id;
        this.creatorId = creatorId;
        this.participantId = participantId;
        this.sport = sport;
        this.city = city;
        this.status = status;
        this.completedDate = completedDate;
    }

    /** Constructor without ID for creating workout entity. */
    public Workout(int creatorId, int participantId, Sports sport, String city, WorkoutStatus status, LocalDate completedDate) {
        this.creatorId = creatorId;
        this.participantId = participantId;
        this.sport = sport;
        this.city = city;
        this.status = status;
        this.completedDate = completedDate;
    }

    /** Constructor without ID for creating new workouts before saving to DB. */
    public Workout(int creatorId, int participantId, Sports sport, String city, WorkoutStatus status) {
        this.creatorId = creatorId;
        this.participantId = participantId;
        this.sport = sport;
        this.city = city;
        this.status = status;
        this.completedDate = null;
    }

    public int getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public Sports getSport() {
        return sport;
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
