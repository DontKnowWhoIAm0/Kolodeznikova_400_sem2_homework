package ru.kpfu.itis.Kolodeznikova.dto;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

import java.time.LocalDate;

public class WorkoutRequestDto {

    private WorkoutRequest request;
    private User creator;
    private boolean hasResponded;
    private LocalDate startDate;

    public WorkoutRequestDto(WorkoutRequest request, User creator, LocalDate date, boolean hasResponded) {
        this.request = request;
        this.creator = creator;
        this.hasResponded = hasResponded;
        this.startDate = date;
    }

    public WorkoutRequest getRequest() { return request; }
    public User getCreator() { return creator; }
    public LocalDate getStartDate() { return startDate; }
    public boolean isHasResponded() { return hasResponded; }

}
