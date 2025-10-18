package ru.kpfu.itis.Kolodeznikova.dao;

import ru.kpfu.itis.Kolodeznikova.entity.WorkoutRequest;

import java.sql.SQLException;

/**
 * DAO interface for working with WorkoutRequest entities.
 */
public interface WorkoutRequestDao extends Dao<WorkoutRequest> {
    void addRespondentToRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
    void deleteRespondentFromRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
}
