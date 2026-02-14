package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Service interface for managing Workout entities.
 */
public interface WorkoutService {
    Workout findById(int workoutId) throws SQLException;
    void createWorkout(Workout workout, WorkoutRequest request) throws SQLException;
    void updateWorkout(Workout workout) throws SQLException;
    void deleteWorkout(int workoutId) throws SQLException;
    List<Workout> getAllUserWorkouts(int userId) throws SQLException;
    List<Workout> getAllUserAsCreatorPendingWorkouts(int userId) throws SQLException;
    List<Workout> getAllUserAsParticipantPendingWorkouts(int userId) throws SQLException;
}
