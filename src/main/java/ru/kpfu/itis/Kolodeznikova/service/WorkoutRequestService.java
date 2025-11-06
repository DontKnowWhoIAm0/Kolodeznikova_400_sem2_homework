package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.dto.WorkoutRequestDto;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Service interface for managing Workout Request entities.
 */
public interface WorkoutRequestService {
    WorkoutRequest findById(int workoutRequestId) throws SQLException;
    void createWorkoutRequest(WorkoutRequest workoutRequest) throws SQLException;
    void deleteWorkoutRequest(WorkoutRequest workoutRequest) throws SQLException;
    void addRespondentToRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
    void deleteRespondentFromRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
    List<WorkoutRequest> getAllNotUserRequests(int userId) throws SQLException;
    List<WorkoutRequestDto> getAllNotUserRequestsDto(int userId) throws SQLException;
    List<WorkoutRequestDto> getUserRequestsDto(int userId) throws SQLException;
    List<WorkoutRequestDto> getAllForeignRequestsDto(int userId) throws SQLException;
}
