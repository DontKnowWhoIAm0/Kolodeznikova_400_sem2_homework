package ru.kpfu.itis.Kolodeznikova.servlet.main;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that handles the page displaying all user's workouts.
 */
@WebServlet(name="Workouts", urlPatterns = "/workouts")
public class WorkoutsPageServlet extends HttpServlet {

    private WorkoutService workoutService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.workoutService = (WorkoutService) config.getServletContext().getAttribute("workoutService");
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", "Тренировки");
        req.setAttribute("contextPath", req.getContextPath());

        try {
            String login = (String) req.getSession().getAttribute("login");
            int userId = userService.findUserIdByLogin(login);

            List<Workout> workouts = workoutService.getAllUserWorkouts(userId);

            Map<Integer, User> usersMap = new HashMap<>();
            for (Workout workout : workouts) {
                int creatorId = workout.getCreatorId();
                int participantId = workout.getParticipantId();

                if (!usersMap.containsKey(creatorId)) {
                    usersMap.put(creatorId, userService.findUserById(creatorId));
                }
                if (!usersMap.containsKey(participantId)) {
                    usersMap.put(participantId, userService.findUserById(participantId));
                }
            }

            req.setAttribute("workouts", workouts);
            req.setAttribute("usersMap", usersMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/WEB-INF/templates/main/main.ftl").forward(req, resp);
    }

}
