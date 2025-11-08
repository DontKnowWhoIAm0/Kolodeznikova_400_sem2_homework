package ru.kpfu.itis.Kolodeznikova.servlet.profile;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
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
 * Servlet that handles user profile page.
 */
@WebServlet(name = "Profile Page    ", urlPatterns = "/profile")
public class ProfilePageServlet extends HttpServlet {

    private UserService userService;
    private WorkoutService workoutService;

    /**
     * Initializes the servlet and gets the UserService and WorkoutService instances from the servlet context.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
        this.workoutService = (WorkoutService) config.getServletContext().getAttribute("workoutService");
    }

    /**
     * Handles GET requests for the profile page.
     * Retrieves user information and their workouts, then forwards to profile_page.ftl.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", "Профиль");
        req.setAttribute("contextPath", req.getContextPath());

        Integer userId = (Integer) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            User user = userService.findUserById(userId);
            List<Workout> workouts = workoutService.getAllUserWorkouts(userId);

            Map<String, User> usersMap = new HashMap<>();
            for (Workout workout : workouts) {
                int creatorId = workout.getCreatorId();
                int participantId = workout.getParticipantId();

                if (!usersMap.containsKey(creatorId)) {
                    usersMap.put(String.valueOf(creatorId), userService.findUserById(creatorId));
                }
                if (participantId != 0 && !usersMap.containsKey(participantId)) {
                    usersMap.put(String.valueOf(participantId), userService.findUserById(participantId));
                }
            }

            req.setAttribute("user", user);
            req.setAttribute("userId", userId);
            req.setAttribute("workouts", workouts);
            req.setAttribute("usersMap", usersMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/WEB-INF/templates/main/main.ftl").forward(req, resp);
    }
}
