package ru.kpfu.itis.Kolodeznikova.servlet.requests;

import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.entity.enums.Sports;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Servlet that handles workout request creation functionality.
 */
@WebServlet(name = "Create Request", urlPatterns = "/createRequest")
public class CreateRequestServlet extends HttpServlet {

    private WorkoutRequestService workoutRequestService;
    private UserService userService;

    /**
     * Initializes the servlet and gets the UserService and WorkoutRequestService instances from the servlet context.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.workoutRequestService = (WorkoutRequestService) config.getServletContext().getAttribute("workoutRequestService");
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    /**
     * Handles GET requests by forwarding the user to the request creation page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contextPath", req.getContextPath());
        req.setAttribute("sportsList", Sports.values());
        req.getRequestDispatcher("/WEB-INF/templates/requests/create_request_page.ftl").forward(req, resp);
    }

    /**
     * Handles POST requests for creating a new workout request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sportStr = req.getParameter("sport");
        String city = req.getParameter("city");
        String description = req.getParameter("description");
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");
        String startTimeStr = req.getParameter("startTime");
        String endTimeStr = req.getParameter("endTime");
        String isTimeRelevantStr = req.getParameter("isTimeRelevant");

        if (sportStr == null || sportStr.isBlank() ||
                city == null || city.isBlank() ||
                startDateStr == null || startDateStr.isBlank() ||
                endDateStr == null || endDateStr.isBlank()) {

            req.setAttribute("error", "Заполнены не все обязательные поля");
            req.setAttribute("contextPath", req.getContextPath());
            req.setAttribute("sportsList", Sports.values());
            req.getRequestDispatcher("/WEB-INF/templates/requests/create_request_page.ftl").forward(req, resp);
            return;
        }

        Sports sport = Sports.valueOf(sportStr);
        boolean isTimeRelevant = Boolean.parseBoolean(isTimeRelevantStr);

        LocalDate startDate;
        LocalDate endDate;
        LocalTime endTime = null;
        LocalTime startTime = null;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);

            if (isTimeRelevant) {
                if (startTimeStr != null && !startTimeStr.isBlank()) {
                    startTime = LocalTime.parse(startTimeStr + ":00");
                }
                if (endTimeStr != null && !endTimeStr.isBlank()) {
                    endTime = LocalTime.parse(endTimeStr + ":00");
                }
            }

            if (endDate.isBefore(startDate)) {
                req.setAttribute("error", "Дата окончания не может быть раньше даты начала");
                req.setAttribute("contextPath", req.getContextPath());
                req.setAttribute("sportsList", Sports.values());
                req.getRequestDispatcher("/WEB-INF/templates/requests/create_request_page.ftl").forward(req, resp);
                return;
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Некорректный формат даты или времени");
            req.setAttribute("contextPath", req.getContextPath());
            req.setAttribute("sportsList", Sports.values());
            req.getRequestDispatcher("/WEB-INF/templates/requests/create_request_page.ftl").forward(req, resp);
            return;
        }

        int creatorId = (Integer) req.getSession().getAttribute("userId");

        WorkoutRequest workoutRequest = new WorkoutRequest(
                creatorId, List.of(), sport, description, city,
                startDate, endDate, isTimeRelevant, startTime, endTime
        );

        try {
            workoutRequestService.createWorkoutRequest(workoutRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("main");
    }

}

