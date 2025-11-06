package ru.kpfu.itis.Kolodeznikova.servlet.main;

import ru.kpfu.itis.Kolodeznikova.dto.WorkoutRequestDto;
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

@WebServlet(name = "Requests", urlPatterns = "/requests")
public class RequestsPageServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("title", "Запросы");
        req.setAttribute("contextPath", req.getContextPath());

        Integer userId = (Integer) req.getSession().getAttribute("userId");

        try {
            List<WorkoutRequestDto> myRequests = workoutRequestService.getUserRequestsDto(userId);

            List<WorkoutRequestDto> allForeign = workoutRequestService.getAllForeignRequestsDto(userId);

            List<WorkoutRequestDto> respondedRequests = allForeign.stream()
                    .filter(WorkoutRequestDto::isHasResponded)
                    .collect(Collectors.toList());

            req.setAttribute("userId", userId);
            req.setAttribute("respondedRequests", respondedRequests);
            req.setAttribute("userRequests", myRequests);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/WEB-INF/templates/main/main.ftl").forward(req, resp);
    }
}
