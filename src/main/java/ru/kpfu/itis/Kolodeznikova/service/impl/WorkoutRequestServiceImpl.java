package ru.kpfu.itis.Kolodeznikova.service.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutRequestDao;
import ru.kpfu.itis.Kolodeznikova.dto.WorkoutRequestDto;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.NotificationService;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;
import ru.kpfu.itis.Kolodeznikova.util.NotificationMessageBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides high-level operations for managing WorkoutRequest entities.
 */
public class WorkoutRequestServiceImpl implements WorkoutRequestService {

    private final WorkoutRequestDao workoutRequestDao;
    private final UserService userService;
    private final NotificationService notificationService;

    public WorkoutRequestServiceImpl(WorkoutRequestDao workoutRequestDao, UserService userService, NotificationService notificationService) {
        this.workoutRequestDao = workoutRequestDao;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    /**
     * Finds a workout request by its unique ID.
     */
    @Override
    public WorkoutRequest findById(int workoutRequestId) throws SQLException {
        return workoutRequestDao.findById(workoutRequestId);
    }

    /**
     * Adds a new workout request in the database.
     */
    @Override
    public void createWorkoutRequest(WorkoutRequest workoutRequest) throws SQLException {
        workoutRequestDao.create(workoutRequest);
    }

    /**
     * Deletes an existing workout request from the database.
     */
    @Override
    public void deleteWorkoutRequest(WorkoutRequest workoutRequest, boolean flag) throws SQLException {
        List<Integer> respondents = workoutRequest.getRespondentsId();

        if (!respondents.isEmpty() && flag) {
            String nickname = userService.findUserById(workoutRequest.getCreatorId()).getNickname();
            String notificationText = NotificationMessageBuilder.buildDeleteRequestNotification(nickname, workoutRequest);
            notificationService.addNotification(workoutRequest.getCreatorId(), notificationText, respondents);
        }

        workoutRequestDao.delete(workoutRequest.getId());
    }

    /**
     * Adds a respondent to an existing workout request.
     */
    @Override
    public void addRespondentToRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException {
        workoutRequestDao.addRespondentToRequest(workoutRequest, respondentId);
        String nickname = userService.findUserById(respondentId).getNickname();
        String notificationText = NotificationMessageBuilder.buildResponseNotification(nickname, workoutRequest);
        int creatorId = workoutRequest.getCreatorId();
        List<Integer> recipientIds = new ArrayList<>();
        recipientIds.add(creatorId);
        notificationService.addNotification(respondentId, notificationText, recipientIds);
    }

    /**
     * Removes a respondent from a workout request.
     */
    @Override
    public void deleteRespondentFromRequest(WorkoutRequest workoutRequest, int respondentId, boolean flag) throws SQLException {
        workoutRequestDao.deleteRespondentFromRequest(workoutRequest, respondentId);
        String nickname = userService.findUserById(respondentId).getNickname();
        String notificationText = "";
        if (flag) {
            notificationText = NotificationMessageBuilder.buildCancelResponseNotification(nickname, workoutRequest);
        } else {
            notificationText = NotificationMessageBuilder.buildDeleteResponseNotification(nickname, workoutRequest);
        }
        List<Integer> recipientIds = new ArrayList<>();
        recipientIds.add(respondentId);
        notificationService.addNotification(workoutRequest.getCreatorId(), notificationText, recipientIds);
    }

    /**
     * Returns a list of all requests that was created by other users.
     */
    @Override
    public List<WorkoutRequest> getAllNotUserRequests(int userId) throws SQLException {
        return workoutRequestDao.getAllNotUserRequests(userId);
    }

    @Override
    public List<WorkoutRequestDto> getAllNotUserRequestsDto(int userId) throws SQLException {
        List<WorkoutRequest> requests = workoutRequestDao.getAllNotUserRequests(userId);

        return requests.stream()
                .map(req -> {
                    try {
                        User creator = userService.findUserById(req.getCreatorId());
                        boolean hasResponded = req.getRespondentsId() != null && req.getRespondentsId().contains(userId);
                        return new WorkoutRequestDto(req, creator, hasResponded);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutRequestDto> getUserRequestsDto(int userId) throws SQLException {
        List<WorkoutRequest> myRequests = workoutRequestDao.getAllUserRequests(userId);

        return myRequests.stream()
                .map(req -> {
                    try {
                        return new WorkoutRequestDto(req, userService.findUserById(userId), false);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutRequestDto> getAllForeignRequestsDto(int userId) throws SQLException {
        List<WorkoutRequest> requests = workoutRequestDao.getAllForeignRequests(userId);

        return requests.stream()
                .map(req -> {
                    try {
                        return new WorkoutRequestDto(
                                req,
                                userService.findUserById(req.getCreatorId()),
                                req.getRespondentsId().contains(userId)
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

}
