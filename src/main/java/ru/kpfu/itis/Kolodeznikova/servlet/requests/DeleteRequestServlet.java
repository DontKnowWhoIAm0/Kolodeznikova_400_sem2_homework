package ru.kpfu.itis.Kolodeznikova.servlet.requests;

import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="Delete Request", urlPatterns = "/deleteRequest")
public class DeleteRequestServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;

    @Override
    public void init(ServletConfig config) {
        workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(req.getParameter("requestId"));
            int userId = (Integer) req.getSession().getAttribute("userId");

            WorkoutRequest request = workoutRequestService.findById(requestId);

            if (request.getCreatorId() != userId) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            workoutRequestService.deleteWorkoutRequest(request, true);

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
