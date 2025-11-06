package ru.kpfu.itis.Kolodeznikova.servlet.requests;

import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="Respond Request", urlPatterns = "/respondRequest")
public class RespondRequestServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(req.getParameter("requestId"));

            String login = (String) req.getSession().getAttribute("login");
            int userId = userService.findUserIdByLogin(login);

            WorkoutRequest request = workoutRequestService.findById(requestId);
            workoutRequestService.addRespondentToRequest(request, userId);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
