package ru.kpfu.itis.Kolodeznikova.entity;

import ru.kpfu.itis.Kolodeznikova.entity.enums.Sports;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents a workout request created by a user.
 * A request includes a sport, city and date range.
 * The request also keeps track of users who have responded to it.
 */
public class WorkoutRequest {

    /** Unique identifier of the workout request. */
    private int id;

    /** ID of the user who created the request. */
    private int creatorId;

    /** List of IDs of users who have responded to the workout request. */
    private List<Integer> respondentsId;

    /** The sport chosen for the workout request. */
    private Sports sports;

    /** The city where the workout is planned. */
    private String city;

    /** Start date of the period during which the creator would like to have the workout. */
    private LocalDate startDate;

    /** End date of the period during which the creator would like to have the workout. */
    private LocalDate endDate;

    /** Indicates whether the startTime and endTime fields are relevant.
     * Time selection is only available if the workout period consists of a single day. */
    private boolean isTimeRelevant;

    /** Start time of the period during which the creator would like to have the workout. */
    private LocalTime startTime;

    /** End time of the period during which the creator would like to have the workout. */
    private LocalTime endTime;

    /** Constructor including ID. */
    public WorkoutRequest(int id, int creatorId, List<Integer> respondentsId, Sports sports, String city, LocalDate startDate, LocalDate endDate, boolean isTimeRelevant, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.creatorId = creatorId;
        this.respondentsId = respondentsId;
        this.sports = sports;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isTimeRelevant = isTimeRelevant;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Constructor without ID for creating new requests before saving to DB. */
    public WorkoutRequest(int creatorId, List<Integer> respondentsId, Sports sports, String city, LocalDate startDate, LocalDate endDate, boolean isTimeRelevant, LocalTime startTime, LocalTime endTime) {
        this.creatorId = creatorId;
        this.respondentsId = respondentsId;
        this.sports = sports;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isTimeRelevant = isTimeRelevant;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public List<Integer> getRespondentsId() {
        return respondentsId;
    }

    public Sports getSports() {
        return sports;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isTimeRelevant() {
        return isTimeRelevant;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
