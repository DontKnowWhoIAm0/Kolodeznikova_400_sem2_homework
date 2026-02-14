package ru.kpfu.itis.Kolodeznikova.servlet.auth.ajax;

import ru.kpfu.itis.Kolodeznikova.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet for checking login availability using AJAX request.
 * Responds with «exists» if the login is already taken and «free» if it is available.
 */
@WebServlet(urlPatterns = "/ajax/checkLogin")
public class CheckLoginServlet extends HttpServlet {

    private UserService userService;

    /**
     * Initializes the servlet and gets the UserService instance from the servlet context.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    /**
     * Handles GET requests to check login availability.
     * Retrieves the «login» parameter from the request and checks if it exists in the database.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get the login parameter from the request and set response type
        String login = req.getParameter("login");
        resp.setContentType("text/plain");

        // Check if the login already exists in the database
        boolean exists = false;
        try {
            exists = userService.loginExists(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Send the response to the client: «exists» or «free»
        resp.getWriter().write(exists ? "exists" : "free");
    }

}
