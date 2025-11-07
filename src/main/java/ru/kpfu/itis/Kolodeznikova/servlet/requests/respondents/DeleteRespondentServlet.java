package ru.kpfu.itis.Kolodeznikova.servlet.requests.respondents;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Delete Respondent", urlPatterns = "/deleteRespondent")
public class DeleteRespondentServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) {
        workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            Map<String, Object> body = objectMapper.readValue(req.getInputStream().readAllBytes(), Map.class);
            int requestId = Integer.parseInt(body.get("requestId").toString());
            int respondentId = Integer.parseInt(body.get("respondentId").toString());

            WorkoutRequest request = workoutRequestService.findById(requestId);

            if (request != null) {
                workoutRequestService.deleteRespondentFromRequest(request, respondentId, false);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
