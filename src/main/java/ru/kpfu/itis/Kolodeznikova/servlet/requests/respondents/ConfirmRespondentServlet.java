package ru.kpfu.itis.Kolodeznikova.servlet.requests.respondents;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.entity.enums.WorkoutStatus;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Confirm Respondent", urlPatterns = "/confirmRespondent")
public class ConfirmRespondentServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;
    private WorkoutService workoutService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) {
        workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
        workoutService = (WorkoutService) config.getServletContext().getAttribute("workoutService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Map<String, Object> body = objectMapper.readValue(req.getInputStream(), Map.class);
            int requestId = Integer.parseInt(body.get("requestId").toString());
            int respondentId = Integer.parseInt(body.get("respondentId").toString());

            WorkoutRequest request = workoutRequestService.findById(requestId);
            if (request == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Workout workout = new Workout(
                    request.getCreatorId(),
                    respondentId,
                    request.getSport(),
                    request.getCity(),
                    WorkoutStatus.PENDING
            );
            workoutService.createWorkout(workout, request);

            for (Integer id : request.getRespondentsId()) {
                workoutRequestService.deleteRespondentFromRequest(request, id, false);
            }
            workoutRequestService.deleteWorkoutRequest(request, false);

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
