package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;

import java.sql.SQLException;
import java.util.List;

public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutDao workoutDao;

    public WorkoutServiceImpl(WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
    }

    @Override
    public Workout findById(int workoutId) throws SQLException {
        return workoutDao.findById(workoutId);
    }

    @Override
    public void createWorkout(Workout workout) throws SQLException {
        workoutDao.create(workout);
    }

    @Override
    public void updateWorkout(Workout workout) throws SQLException {
        workoutDao.update(workout);
    }

    @Override
    public void deleteWorkout(int workoutId) throws SQLException {
        workoutDao.delete(workoutId);
    }

    @Override
    public List<Workout> getAllUserWorkouts(int userId) throws SQLException {
        return workoutDao.getAllUserWorkouts(userId);
    }

    @Override
    public List<Workout> getAllUserAsCreatorPendingWorkouts(int userId) throws SQLException {
        return workoutDao.getAllUserAsCreatorPendingWorkouts(userId);
    }

    @Override
    public List<Workout> getAllUserAsParticipantPendingWorkouts(int userId) throws SQLException {
        return workoutDao.getAllUserAsParticipantPendingWorkouts(userId);
    }

}
