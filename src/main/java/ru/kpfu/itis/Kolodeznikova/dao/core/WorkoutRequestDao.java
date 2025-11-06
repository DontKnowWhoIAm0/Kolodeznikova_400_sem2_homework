package ru.kpfu.itis.Kolodeznikova.dao.core;

import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for working with WorkoutRequest entities.
 */
public interface WorkoutRequestDao extends Dao<WorkoutRequest> {
    void addRespondentToRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
    void deleteRespondentFromRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException;
    List<WorkoutRequest> getAllNotUserRequests(int userId) throws SQLException;
    List<WorkoutRequest> getAllUserRequests(int userId) throws SQLException;
    List<WorkoutRequest> getAllForeignRequests(int userId) throws SQLException;
}
