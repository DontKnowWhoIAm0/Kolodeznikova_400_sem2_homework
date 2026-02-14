package ru.kpfu.itis.Kolodeznikova.servlet.workouts;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.enums.WorkoutStatus;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet(name = "UpdateWorkoutStatusServlet", urlPatterns = "/updateWorkoutStatus")
public class UpdateWorkoutStatusServlet extends HttpServlet {

    private WorkoutService workoutService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) {
        workoutService = (WorkoutService) config.getServletContext().getAttribute("workoutService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Map<String, Object> body = objectMapper.readValue(req.getInputStream(), Map.class);
            int workoutId = Integer.parseInt(body.get("workoutId").toString());
            String statusStr = body.get("status").toString();

            Workout workout = workoutService.findById(workoutId);
            if (workout == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            WorkoutStatus status;
            try {
                status = WorkoutStatus.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            workout.setStatus(status);
            if (status == WorkoutStatus.CANCELED || status == WorkoutStatus.COMPLETED) {
                workout.setCompletedDate(LocalDate.now());
            } else {
                workout.setCompletedDate(null);
            }

            workoutService.updateWorkout(workout);

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
