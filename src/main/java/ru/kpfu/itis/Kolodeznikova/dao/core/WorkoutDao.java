package ru.kpfu.itis.Kolodeznikova.dao.core;

import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for working with Workout entities.
 */
public interface WorkoutDao extends Dao<Workout> {
    void update(Workout workout) throws SQLException;
    List<Workout> getAllUserWorkouts(int id) throws SQLException;
    List<Workout> getAllUserAsCreatorPendingWorkouts(int id) throws SQLException;
    List<Workout> getAllUserAsParticipantPendingWorkouts(int id) throws SQLException;
}
