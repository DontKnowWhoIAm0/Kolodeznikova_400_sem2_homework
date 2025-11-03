package ru.kpfu.itis.Kolodeznikova.servlet.main;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet that handles functionality of main page.
 */
@WebServlet(name="Main", urlPatterns = "/main")
public class MainPageServlet extends HttpServlet {

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
     * Handles GET requests for the main page.
     * Retrieves a list of workout requests that do not belong to the current user
     * and forwarded it to the main page template.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contextPath", req.getContextPath());

        try {
            List<WorkoutRequest> requests = workoutRequestService.getAllNotUserRequests(
                    userService.findUserIdByLogin((String) req.getSession().getAttribute("login")));

            Map<String, User> creatorsMap = new HashMap<>();
            for (WorkoutRequest r : requests) {
                int creatorId = r.getCreatorId();
                if (!creatorsMap.containsKey(String.valueOf(creatorId))) {
                    creatorsMap.put(String.valueOf(creatorId), userService.findUserById(creatorId));
                }
            }

            req.setAttribute("requests", requests);
            req.setAttribute("creatorsMap", creatorsMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/WEB-INF/templates/main/main.ftl").forward(req, resp);
    }
}
