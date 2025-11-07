package ru.kpfu.itis.Kolodeznikova.util;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;

public class NotificationMessageBuilder {

    public static String buildResponseNotification(String nickname, WorkoutRequest workoutRequest) {
        String notificationText = "Пользователь " + nickname + " откликнулся на ваш запрос «" + workoutRequest.getSport().getName() + ", ";

        if (workoutRequest.isTimeRelevant()) {
            notificationText += workoutRequest.getStartDate() + ", " + workoutRequest.getStartTime() + "—" + workoutRequest.getEndTime() + "»";
        } else {
            notificationText += workoutRequest.getStartDate() + "—" + workoutRequest.getEndDate() + "»";
        }

        return notificationText;
    }

    public static String buildCancelResponseNotification(String nickname, WorkoutRequest workoutRequest) {
        String notificationText = "Пользователь " + nickname + " отменил отклик на ваш запрос «" + workoutRequest.getSport().getName() + ", ";

        if (workoutRequest.isTimeRelevant()) {
            notificationText += workoutRequest.getStartDate() + ", " + workoutRequest.getStartTime() + "—" + workoutRequest.getEndTime() + "»";
        } else {
            notificationText += workoutRequest.getStartDate() + "—" + workoutRequest.getEndDate() + "»";
        }

        return notificationText;
    }

    public static String buildDeleteRequestNotification(String nickname, WorkoutRequest workoutRequest) {
        String notificationText = "Пользователь " + nickname + " удалил свой запрос «" + workoutRequest.getSport().getName() + ", ";

        if (workoutRequest.isTimeRelevant()) {
            notificationText += workoutRequest.getStartDate() + ", " + workoutRequest.getStartTime() + "—" + workoutRequest.getEndTime() + "»";
        } else {
            notificationText += workoutRequest.getStartDate() + "—" + workoutRequest.getEndDate() + "»";
        }

        return notificationText;
    }

    public static String buildDeleteResponseNotification(String nickname, WorkoutRequest workoutRequest) {
        String notificationText = "К сожалению, пользователь " + nickname + " не хочет проводить тренировку «" + workoutRequest.getSport().getName() + ", ";

        if (workoutRequest.isTimeRelevant()) {
            notificationText += workoutRequest.getStartDate() + ", " + workoutRequest.getStartTime() + "—" + workoutRequest.getEndTime() + "»";
        } else {
            notificationText += workoutRequest.getStartDate() + "—" + workoutRequest.getEndDate() + "»";
        }

        notificationText += " с Вами";

        return notificationText;
    }

    public static String buildConfirmResponseNotification(User sender, WorkoutRequest workoutRequest) {
        String notificationText = "Пользователь " + sender.getNickname() + " хочет провести с Вами тренировку «" + workoutRequest.getSport().getName() + ", ";

        if (workoutRequest.isTimeRelevant()) {
            notificationText += workoutRequest.getStartDate() + ", " + workoutRequest.getStartTime() + "—" + workoutRequest.getEndTime() + "»";
        } else {
            notificationText += workoutRequest.getStartDate() + "—" + workoutRequest.getEndDate() + "»";
        }

        notificationText += ". Пожалуйста, свяжитесь с ним! Способ связи: " + sender.getContactValue() + ", " + sender.getWayOfCommunication().getDisplayName();


        return notificationText;
    }

    public static String buildWorkoutStatusChangeNotification(String nickname, Workout workout) {
        return "Пользователь " + nickname + " обновил статус тренировки «" + workout.getSport().getName() + ", " + workout.getCity() + "»";
    }

}
