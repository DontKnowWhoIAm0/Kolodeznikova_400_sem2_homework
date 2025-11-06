package ru.kpfu.itis.Kolodeznikova.dto;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

public class WorkoutRequestDto {

    private WorkoutRequest request;
    private User creator;
    private boolean hasResponded;

    public WorkoutRequestDto(WorkoutRequest request, User creator, boolean hasResponded) {
        this.request = request;
        this.creator = creator;
        this.hasResponded = hasResponded;
    }

    public WorkoutRequest getRequest() { return request; }
    public User getCreator() { return creator; }
    public boolean isHasResponded() { return hasResponded; }

}
