package ru.kpfu.itis.Kolodeznikova.servlet.requests.respondents;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Request Respondents", urlPatterns = "/requestRespondents")
public class RequestRespondentsServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(req.getParameter("requestId"));

            WorkoutRequest request = workoutRequestService.findById(requestId);

            List<User> respondents;
            if (request.getRespondentsId() != null && !request.getRespondentsId().isEmpty()) {
                respondents = request.getRespondentsId().stream()
                        .map(id -> {
                            try {
                                return userService.findUserById(id);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList());
            } else {
                respondents = List.of();
            }

            req.setAttribute("title", "Отклики на запрос");
            req.setAttribute("request", request);
            req.setAttribute("creator", userService.findUserById(request.getCreatorId()));
            req.setAttribute("contextPath", req.getContextPath());
            req.setAttribute("respondents", respondents);

            req.getRequestDispatcher("/WEB-INF/templates/requests/respondent_list.ftl").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный requestId");
        }
    }
}