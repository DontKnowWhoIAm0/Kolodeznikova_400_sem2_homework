package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.NotificationService;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;
import ru.kpfu.itis.Kolodeznikova.util.NotificationMessageBuilder;

import java.sql.SQLException;
import java.util.List;

public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutDao workoutDao;
    private final UserService userService;
    private final NotificationService notificationService;

    public WorkoutServiceImpl(WorkoutDao workoutDao, UserService userService, NotificationService notificationService) {
        this.workoutDao = workoutDao;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public Workout findById(int workoutId) throws SQLException {
        return workoutDao.findById(workoutId);
    }

    @Override
    public void createWorkout(Workout workout, WorkoutRequest request) throws SQLException {
        workoutDao.create(workout);

        int senderId = workout.getCreatorId();
        User sender = userService.findUserById(senderId);
        String notificationText = NotificationMessageBuilder.buildConfirmResponseNotification(sender, request);

        notificationService.addNotification(senderId, notificationText, List.of(workout.getParticipantId()));
    }

    @Override
    public void updateWorkout(Workout workout) throws SQLException {
        workoutDao.update(workout);

        int senderId = workout.getCreatorId();
        User sender = userService.findUserById(senderId);
        String notificationText = NotificationMessageBuilder.buildWorkoutStatusChangeNotification(sender.getNickname(), workout);

        notificationService.addNotification(senderId, notificationText, List.of(workout.getParticipantId()));
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
